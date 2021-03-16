package dev.ch8n.toiweather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.distinctUntilChanged
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.jraska.livedata.test
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import dev.ch8n.toiweather.data.remote.repos.WeatherRepo
import dev.ch8n.toiweather.utils.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var weatherRepo: WeatherRepo
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        weatherRepo = mockk(relaxed = true)
        mainViewModel = MainViewModel(weatherRepo)
    }

    @Test
    fun `when weather response success`() = runBlocking {
        coEvery { weatherRepo.getRemoteCurrentWeather(any()) } returns WeatherResponse.fake()
        mainViewModel.response
            .test()
            .awaitValue()
            .assertValue { result: Result<WeatherResponse, Exception>? ->
                val current = (result as Result.Success).value.current
                current?.cloudcover == 10 &&
                        current.precip == 10 &&
                        current.pressure == 10 &&
                        current.temperature == 10 &&
                        current.windSpeed == 10 &&
                        current.weatherDescriptions?.get(0) == "rain"
            }

        mainViewModel.getCurrentWeather("delhi")
    }


    @Test
    fun `when weather response fails`() = runBlocking {
        coEvery { weatherRepo.getRemoteCurrentWeather(any()) } throws Exception("pokemon")

        val slots = mutableListOf<Result<WeatherResponse, Exception>>()
        mainViewModel.response.observeForever {
            slots.add(it)
            if (slots.size == 2) {
                val result = slots.get(1)
                Truth.assertThat(result is Result.Error).isTrue()
            }
        }
        mainViewModel.getCurrentWeather("delhi")


    }

}