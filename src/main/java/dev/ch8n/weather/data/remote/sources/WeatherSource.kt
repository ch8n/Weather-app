package dev.ch8n.weather.data.remote.sources

import dev.ch8n.weather.data.remote.config.ApiManager
import javax.inject.Inject
import javax.inject.Singleton


class WeatherSource constructor(private val apiManager: ApiManager) {
    suspend fun getWeatherInfo() = apiManager.weatherServices.getWeatherInfo()
}