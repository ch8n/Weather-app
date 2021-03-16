package dev.ch8n.toiweather.ui

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.ch8n.toiweather.R
import dev.ch8n.toiweather.base.ViewBindingActivity
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import dev.ch8n.toiweather.databinding.ActivityMainBinding
import dev.ch8n.toiweather.databinding.LayoutErrorBinding
import dev.ch8n.toiweather.databinding.LayoutLoadingBinding
import dev.ch8n.toiweather.databinding.LayoutMainContentBinding
import dev.ch8n.toiweather.ui.adapter.ForcastItem
import dev.ch8n.toiweather.ui.adapter.ForcastListAdapter
import dev.ch8n.toiweather.utils.Result
import dev.ch8n.toiweather.utils.isVisible
import dev.ch8n.toiweather.utils.startRotation
import dev.ch8n.toiweather.utils.stopRotation
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
    private var bottomSheet: BottomSheetBehavior<ViewGroup>? = null

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

            // using bottom sheet for expanding behaviour
            bottomSheet = BottomSheetBehavior.from(mainView.layoutBottomSheet)

            // initial api call
            viewModel.getCurrentWeather("New Delhi")

            // error-retry
            errorView.btnRetry.setOnClickListener {
                viewModel.retryWeatherFetch()
            }

            // testing purpose
            mainView.textCurrentTemp.setOnClickListener {
                viewModel.retryWeatherFetch()
            }

        }

    }

    private fun onSuccessWeatherInfo(
        weather: WeatherResponse,
        viewBinds: ViewBinds
    ) = with(viewBinds) {

        val temp = "${weather.current?.temperature ?: "NA"}${getString(R.string.degree_symbol)}"
        mainView.textCurrentTemp.text = temp
        mainView.textCurrentCity.text = weather.location?.name ?: "NA"
        forcastListAdapter?.submitList(
            listOf(
                ForcastItem("Weather", weather.current?.weatherDescriptions?.getOrNull(0) ?: "N/A"),
                ForcastItem("WindSpeed", "${weather.current?.windSpeed ?: "N/A"}"),
                ForcastItem("Pressure", "${weather.current?.pressure ?: "N/A"}"),
                ForcastItem("Precip", "${weather.current?.pressure ?: "N/A"}"),
                ForcastItem("Cloud Cover", "${weather.current?.cloudcover ?: "N/A"}"),
                ForcastItem("Humidity", "${weather.current?.humidity ?: "N/A"}"),
            )
        )

        // for delayed expansion of bottom sheet for slide-up animation
        viewModel.postDelayed(500) {
            bottomSheet?.state = BottomSheetBehavior.STATE_EXPANDED
        }

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
        bottomSheet = null
        // bindings won't leak no need to release
    }
}
