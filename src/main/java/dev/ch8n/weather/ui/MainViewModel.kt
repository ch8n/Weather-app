package dev.ch8n.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ch8n.weather.data.remote.model.WeatherResponse
import dev.ch8n.weather.data.remote.repos.WeatherRepo
import dev.ch8n.weather.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly

class MainViewModel(private val weatherRepo: WeatherRepo) : ViewModel() {

    private val _result = MutableLiveData<Result<WeatherResponse, Exception>>(Result.Loading)
    val response = _result as LiveData<Result<WeatherResponse, Exception>>

    fun retryWeatherFetch() {
        _result.value = Result.Loading
        getCurrentWeather()
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            // just to make animations visible
            delay(500)
            Result.build { weatherRepo.getRemoteCurrentWeather() }
                .let {
                    _result.postValue(it)
                }
        }
    }

}


