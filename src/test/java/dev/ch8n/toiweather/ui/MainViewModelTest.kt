package dev.ch8n.toiweather.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jraska.livedata.test
import dev.ch8n.toiweather.MainCoroutineRule
import dev.ch8n.toiweather.data.remote.repos.WeatherRepo
import dev.ch8n.toiweather.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainViewModelTest {


    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    lateinit var weatherRepo: WeatherRepo
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        weatherRepo = mockk(relaxed = true)
        mainViewModel = MainViewModel(weatherRepo)
    }

    @Test
    fun getCurrentWeather() {
        runBlocking {

            coEvery { weatherRepo.getRemoteCurrentWeather(any()) } returns WeatherResponse(
                Current(
                    30
                ), Location("india", "delhi")
            )
            mainViewModel.getCurrentWeather("delhi")
                .test()
                .awaitValue()
                .assertValue { result: Result<WeatherResponse, Exception>? ->
                    (result as Result.Success).value.current.temparature == 30
                }
        }
    }


    @Test
    fun getCurrentWeatherException() {
        runBlocking {

            coEvery { weatherRepo.getRemoteCurrentWeather(any()) } throws Exception("pokemon")

            mainViewModel.getCurrentWeather("delhi")
                .test()
                .awaitValue()
                .assertValue { result: Result<WeatherResponse, Exception>? ->
                    (result as Result.Error).error.message?.contains("pokemon")
                }
        }
    }
}