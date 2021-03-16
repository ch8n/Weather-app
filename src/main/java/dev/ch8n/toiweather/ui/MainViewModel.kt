package dev.ch8n.toiweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ch8n.toiweather.data.remote.repos.WeatherRepo
import dev.ch8n.toiweather.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val weatherRepo: WeatherRepo) : ViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun getCurrentWeather(location: String): LiveData<Result<WeatherResponse, Exception>> {
        val result = MutableLiveData<Result<WeatherResponse, Exception>>()
        this.launch {
            Result.build { weatherRepo.getRemoteCurrentWeather(location) }
                .let {
                    result.postValue(it)
                }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

}


