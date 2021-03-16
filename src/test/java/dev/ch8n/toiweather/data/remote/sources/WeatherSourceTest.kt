package dev.ch8n.toiweather.data.remote.sources

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth
import dev.ch8n.toiweather.MainCoroutineRule
import dev.ch8n.toiweather.data.remote.config.ApiManager
import dev.ch8n.toiweather.di.modules.NetworkBinder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@LargeTest
class WeatherSourceTest {


    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    lateinit var weatherSource: WeatherSource

    @Before
    fun setup() {
        val networkBinder = NetworkBinder()
        val okhttp = networkBinder.provideOkHttpClient()
        val retrofit = networkBinder.provideRetrofitClient(okhttp)
        val apiManager = ApiManager(retrofit)
        weatherSource = WeatherSource(apiManager)
    }

    @Test
    fun getWeather() {
        runBlocking {
            val result = weatherSource.getWeatherInfo("New Delhi")
            Truth.assertThat(result.location.name.toLowerCase()).contains("delhi")
        }
    }
}