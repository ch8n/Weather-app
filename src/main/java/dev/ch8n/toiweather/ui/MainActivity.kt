package dev.ch8n.toiweather.ui

import android.os.Handler
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dev.ch8n.toiweather.R
import dev.ch8n.toiweather.base.BaseActivity
import dev.ch8n.toiweather.ui.adapter.ForcastItem
import dev.ch8n.toiweather.ui.adapter.ForcastListAdapter
import dev.ch8n.toiweather.utils.Result
import dev.ch8n.toiweather.utils.isVisible
import dev.ch8n.toiweather.utils.startRotation
import dev.ch8n.toiweather.utils.stopRotation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_main_content.*
import javax.inject.Inject

class MainActivity() : BaseActivity() {

    override val contentView: Int
        get() = R.layout.activity_main

    lateinit var forcastListAdapter: ForcastListAdapter
    lateinit var bottomSheet: BottomSheetBehavior<CardView>

    @Inject
    lateinit var viewModel: MainViewModel

    override fun setup() {

        onLoading(true)

        forcast_list.run {
            adapter = ForcastListAdapter.newInstance().also {
                forcastListAdapter = it
            }
        }

        bottomSheet = BottomSheetBehavior.from(layout_bottomSheet)

        getWeatherResponse()

        btn_retry.setOnClickListener {
            onLoading(true)
            getWeatherResponse()
        }

    }

    fun getWeatherResponse() =
        viewModel.getCurrentWeather("New Delhi").observe(this, Observer { result ->
            when (result) {
                is Result.Success -> onSuccessWeatherInfo(result)
                is Result.Error -> {
                    onError(true)
                    Log.e("response", result.error.localizedMessage)
                }
            }
        })

    fun onSuccessWeatherInfo(result: Result.Success<WeatherResponse>) {

        with(result.value) {
            val temp = "${current.temparature}${getString(R.string.degree_symbol)}"
            text_current_temp.text = temp
            text_current_city.text = location.name
        }

        // TODO: API doesnt support free weather forcast now : https://weatherstack.com/documentation
        // document says : Weather ForecastAvailable on: Professional Plan and higher
        // therefore using dummy data
        forcastListAdapter.submitList(
            listOf(
                ForcastItem("Monday", "28 C"),
                ForcastItem("Tuesday", "28 C"),
                ForcastItem("Wednesday", "28 C"),
                ForcastItem("Thursday", "28 C"),
                ForcastItem("Friday", "28 C"),
                ForcastItem("Saturday", "28 C"),
                ForcastItem("Sunday", "28 C")
            )
        )

        Handler().postDelayed({
            runOnUiThread {
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }, 500)

        onLoading(false)
        Log.e("response", result.value.toString())

    }


    fun onLoading(isLoading: Boolean) {
        layout_loading.isVisible(isLoading)
        layout_error.isVisible(false)
        if (isLoading) {
            image_loading.startRotation()
        } else {
            image_loading.stopRotation()
        }
    }

    fun onError(isError: Boolean) {
        layout_error.isVisible(isError)
        layout_loading.isVisible(false)
    }
}
