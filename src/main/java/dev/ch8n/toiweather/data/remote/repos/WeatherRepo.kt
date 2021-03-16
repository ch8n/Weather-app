package dev.ch8n.toiweather.data.remote.repos

import dev.ch8n.toiweather.data.remote.model.WeatherResponse

interface WeatherRepo {
    suspend fun getRemoteCurrentWeather(location: String): WeatherResponse
}