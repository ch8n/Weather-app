package dev.ch8n.toiweather.ui

import com.jraska.livedata.test
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import dev.ch8n.toiweather.data.remote.repos.WeatherRepo
import dev.ch8n.toiweather.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

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
        mainViewModel.response
            .test()
            .awaitValue()
            .assertValue { result: Result<WeatherResponse, Exception>? ->
                (result as Result.Error).error.message?.contains("pokemon")
            }
        mainViewModel.getCurrentWeather("delhi")
    }

}