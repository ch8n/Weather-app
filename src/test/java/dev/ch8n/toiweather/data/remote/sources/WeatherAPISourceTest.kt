package dev.ch8n.toiweather.data.remote.sources

import androidx.test.filters.LargeTest
import com.google.common.truth.Truth
import dev.ch8n.toiweather.data.remote.config.ApiManager
import dev.ch8n.toiweather.di.modules.NetworkBinder
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


/**
 * Instrumented real api testing useful for quick
 * api response checking
 */
@LargeTest
class WeatherAPISourceTest {

    lateinit var weatherSource: WeatherSource

    @Before
    fun setup() {
        val okhttp = NetworkBinder.provideOkHttpClient()
        val retrofit = NetworkBinder.provideRetrofitClient(okhttp)
        val apiManager = ApiManager(retrofit)
        weatherSource = WeatherSource(apiManager)
    }

    @Test
    fun getWeather() {
        runBlocking {
            val result = weatherSource.getWeatherInfo("New Delhi")
            Truth.assertThat(result.location?.name?.toLowerCase()).contains("delhi")
        }
    }
}