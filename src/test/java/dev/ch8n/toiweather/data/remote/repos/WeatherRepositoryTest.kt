package dev.ch8n.toiweather.data.remote.repos

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import dev.ch8n.toiweather.data.remote.sources.WeatherSource
import dev.ch8n.toiweather.utils.Result
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class WeatherRepositoryTest {


    private lateinit var weatherSource: WeatherSource

    // Set the main coroutines dispatcher for unit testing.
    lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherSource = mockk(relaxed = true)
        weatherRepository = WeatherRepository(weatherSource)
    }

    @Test
    fun `when weather response success`() = runBlocking {
        coEvery { weatherSource.getWeatherInfo(any()) } returns WeatherResponse.fake()
        val response = weatherRepository.getRemoteCurrentWeather("delhi")

        Truth.assertThat(response.current?.temperature).isEqualTo(10)
        Truth.assertThat(response.current?.cloudcover).isEqualTo(10)
        Truth.assertThat(response.current?.precip).isEqualTo(10)
        Truth.assertThat(response.current?.pressure).isEqualTo(10)
        Truth.assertThat(response.current?.temperature).isEqualTo(10)
        Truth.assertThat(response.current?.windSpeed).isEqualTo(10)
        Truth.assertThat(response.current?.weatherDescriptions).isEqualTo(10)
        Truth.assertThat(response.current?.weatherDescriptions?.get(0) == "rain").isTrue()
        Truth.assertThat(response.location?.name).isEqualTo("delhi")
    }

    @Test
    fun `when weather response fails`() = runBlocking {
        coEvery { weatherSource.getWeatherInfo(any()) } throws Exception("Pokemon")
        val result = Result.build {
            weatherRepository.getRemoteCurrentWeather("delhi")
        }
        Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
    }

}


