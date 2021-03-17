package dev.ch8n.toiweather.ui

import androidx.lifecycle.LiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import dev.ch8n.toiweather.data.remote.repos.WeatherRepo
import dev.ch8n.toiweather.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * JVM instrumentation testing without using device
 * for view-model
 */
@RunWith(AndroidJUnit4::class)
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
        mainViewModel.response.awaitSecondResult {
            val current = (it as Result.Success).value.current
            Truth.assertThat(current?.cloudcover).isEqualTo(10)
            Truth.assertThat(current?.precip).isEqualTo(10)
            Truth.assertThat(current?.pressure).isEqualTo(10)
            Truth.assertThat(current?.temperature).isEqualTo(10)
            Truth.assertThat(current?.windSpeed).isEqualTo(10)
            Truth.assertThat(current?.weatherDescriptions?.get(0)).isEqualTo("rain")
        }
        mainViewModel.getCurrentWeather("delhi")
    }


    @Test
    fun `when weather response fails`() = runBlocking {
        coEvery { weatherRepo.getRemoteCurrentWeather(any()) } throws Exception("pokemon")
        mainViewModel.response.awaitSecondResult {
            Truth.assertThat(it is Result.Error).isTrue()
        }
        mainViewModel.getCurrentWeather("delhi")
    }

}


/**
 * Author : chetan
 */
inline fun <T> LiveData<T>.awaitSecondResult(crossinline action: (value: T) -> Unit) {
    val slots = mutableListOf<T>()
    observeForever {
        slots.add(it)
        if (slots.size == 2) {
            val result = slots.get(1)
            action(result)
        }
    }
}