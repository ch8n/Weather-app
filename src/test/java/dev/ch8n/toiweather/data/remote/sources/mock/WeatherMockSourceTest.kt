package dev.ch8n.toiweather.data.remote.sources.mock

import androidx.test.filters.LargeTest
import com.google.common.truth.Truth
import dev.ch8n.toiweather.data.remote.sources.WeatherSource
import dev.ch8n.toiweather.di.modules.NetworkBinder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dev.ch8n.toiweather.utils.Result

/**
 * mock api testing
 * can be merged with end to end test for integrational testing
 */
@LargeTest
class WeatherMockSourceTest {

    lateinit var weatherSource: WeatherSource
    lateinit var mockWebServer: MockWebServer


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        val baseUrl = "/"
        val url = mockWebServer.url(baseUrl)
        val okkHttpClient = NetworkBinder.provideOkHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(url.toString())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okkHttpClient)
            .build()
        val apiManager = NetworkBinder.provideApiManager(retrofit)
        weatherSource = WeatherSource(apiManager)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `mockServer api is responding 200`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponse))
        val result = weatherSource.getWeatherInfo("New Delhi")
        Truth.assertThat(result.location?.name).contains("Delhi")
    }


    @Test
    fun `mockServer api is responding 404 exception would be thrown`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
        val result = Result.build { weatherSource.getWeatherInfo("new delhi") }
        Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
        Truth.assertThat((result as Result.Error).error.toString())
            .contains("HTTP 404 Client Error")
    }

}

val mockResponse: String = """
        {
"request": {
"type": "City",
"query": "New Delhi, India",
"language": "en",
"unit": "m"
},
"location": {
"name": "New Delhi",
"country": "India",
"region": "Delhi",
"lat": "28.600",
"lon": "77.200",
"timezone_id": "Asia/Kolkata",
"localtime": "2021-03-17 00:06",
"localtime_epoch": 1615939560,
"utc_offset": "5.50"
},
"current": {
"observation_time": "06:36 PM",
"temperature": 22,
"weather_code": 143,
"weather_icons": [
"https://assets.weatherstack.com/images/wsymbols01_png_64/wsymbol_0006_mist.png"
],
"weather_descriptions": [
"Haze"
],
"wind_speed": 0,
"wind_degree": 0,
"wind_dir": "N",
"pressure": 1013,
"precip": 0,
"humidity": 65,
"cloudcover": 25,
"feelslike": 22,
"uv_index": 1,
"visibility": 2,
"is_day": "no"
}
}
    """.trimIndent()