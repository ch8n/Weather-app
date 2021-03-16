package dev.ch8n.toiweather.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import dev.ch8n.toiweather.data.remote.repos.WeatherRepo
import dev.ch8n.toiweather.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly

class MainViewModel(private val weatherRepo: WeatherRepo) : ViewModel() {

    private val _result = MutableLiveData<Result<WeatherResponse, Exception>>(Result.Loading)
    val response = _result as LiveData<Result<WeatherResponse, Exception>>
    private var lastWeatherLocation = ""

    @TestOnly
    fun getResultTestOnly() = _result

    fun retryWeatherFetch() {
        _result.value = Result.Loading
        getCurrentWeather(lastWeatherLocation)
    }

    fun getCurrentWeather(location: String) {
        lastWeatherLocation = location
        viewModelScope.launch {
            Result.build { weatherRepo.getRemoteCurrentWeather(location) }
                .let {
                    _result.postValue(it)
                }
        }
    }

    fun postDelayed(timeMillis: Long, action: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            delay(timeMillis)
            action.invoke()
        }
    }

}


