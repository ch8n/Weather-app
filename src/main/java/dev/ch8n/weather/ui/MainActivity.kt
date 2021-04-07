package dev.ch8n.weather.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.ch8n.weather.base.ViewBindingActivity
import dev.ch8n.weather.data.remote.model.Weather
import dev.ch8n.weather.data.remote.model.WeatherResponse
import dev.ch8n.weather.databinding.ActivityMainBinding
import dev.ch8n.weather.databinding.LayoutErrorBinding
import dev.ch8n.weather.databinding.LayoutLoadingBinding
import dev.ch8n.weather.databinding.LayoutMainContentBinding
import dev.ch8n.weather.ui.adapter.ForcastListAdapter
import dev.ch8n.weather.ui.adapter.toWeatherInfo
import dev.ch8n.weather.utils.Result
import dev.ch8n.weather.utils.isVisible
import dev.ch8n.weather.utils.startRotation
import dev.ch8n.weather.utils.stopRotation
import javax.inject.Inject


class MainActivity() : ViewBindingActivity<ActivityMainBinding>() {

    private data class ViewBinds constructor(
        val parent: ActivityMainBinding,
        val mainView: LayoutMainContentBinding,
        val errorView: LayoutErrorBinding,
        val loadingView: LayoutLoadingBinding
    )

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    /**
     * why not lateinit? checkout from my article :https://chetangupta.net/lateinit-vs-null/
     * if you prefer youtube video ==> https://www.youtube.com/watch?v=df1g7_Xq6cc
     * adapter tend to leak and holding view globally isn't a good practice
     * keeping them nullable to release them later
     */
    private var forcastListAdapter: ForcastListAdapter? = null

    @Inject
    lateinit var viewModel: MainViewModel

    override fun setup() {
        // bind views
        val viewBinds = ViewBinds(
            parent = binding,
            mainView = LayoutMainContentBinding.bind(binding.layoutMainContent.root),
            errorView = LayoutErrorBinding.bind(binding.layoutError.root),
            loadingView = LayoutLoadingBinding.bind(binding.layoutLoading.root),
        )

        // attach observers
        viewModel.response.observe(this@MainActivity) { weatherResult ->
            when (weatherResult) {
                is Result.Success -> onSuccessWeatherInfo(weatherResult.value, viewBinds)
                is Result.Error -> onError(true, weatherResult.error, viewBinds)
                is Result.Loading -> onLoading(true, viewBinds)
            }
        }

        with(viewBinds) {

            // prepare adapter
            mainView.forcastList.adapter = ForcastListAdapter.newInstance().also {
                forcastListAdapter = it
            }

            // initial api call
            viewModel.getCurrentWeather()

            // error-retry
            errorView.btnRetry.setOnClickListener {
                viewModel.retryWeatherFetch()
            }

        }

    }

    private fun onSuccessWeatherInfo(
        weather: WeatherResponse,
        viewBinds: ViewBinds
    ) = with(viewBinds) {

        val weatherResponse: List<Weather> = weather.data ?: listOf()
        forcastListAdapter?.submitList(weatherResponse.toWeatherInfo())

        onLoading(false, this)
        Log.e("response", weather.toString())
    }


    private fun onLoading(isLoading: Boolean, viewbinds: ViewBinds) = with(viewbinds) {
        loadingView.root.isVisible(isLoading)
        errorView.root.isVisible(false)
        if (isLoading) {
            loadingView.imageLoading.startRotation()
        } else {
            loadingView.imageLoading.stopRotation()
        }
    }

    private fun onError(isError: Boolean, error: Exception, viewBinds: ViewBinds) {
        viewBinds.errorView.root.isVisible(isError)
        viewBinds.loadingView.root.isVisible(false)
        Log.e("response", error.localizedMessage ?: "unkown error")
    }

    override fun onDestroy() {
        super.onDestroy()
        forcastListAdapter = null
        // bindings won't leak no need to release
    }
}
