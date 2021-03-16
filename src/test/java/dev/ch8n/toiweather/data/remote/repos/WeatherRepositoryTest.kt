package dev.ch8n.toiweather.data.remote.repos

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import dev.ch8n.toiweather.MainCoroutineRule
import dev.ch8n.toiweather.data.remote.sources.WeatherSource
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
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {

        weatherSource = mockk(relaxed = true)
        weatherRepository = WeatherRepository(weatherSource)

    }

    @Test
    fun getRemoteCurrentWeather() = runBlocking {

        coEvery { weatherSource.getWeatherInfo(any()) } returns WeatherResponse(
            Current(
                30
            ), Location("india", "delhi")
        )

        val response = weatherRepository.getRemoteCurrentWeather("delhi")

        coVerify { weatherSource.getWeatherInfo(any()) }
        Truth.assertThat(response.current.temparature).isEqualTo(30)
        Truth.assertThat(response.location.country).isEqualTo("india")
        Truth.assertThat(response.location.name).isEqualTo("delhi")
    }

}


