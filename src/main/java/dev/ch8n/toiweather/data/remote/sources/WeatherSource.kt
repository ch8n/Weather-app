package dev.ch8n.toiweather.data.remote.sources

import dev.ch8n.toiweather.data.remote.config.ApiManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherSource @Inject constructor(private val apiManager: ApiManager) {

    suspend fun getWeatherInfo(location: String) = apiManager.weatherServices.getWeatherInfo(location = location)

}