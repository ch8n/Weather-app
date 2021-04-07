package dev.ch8n.weather.data.remote.repos

import dev.ch8n.weather.data.remote.model.WeatherResponse

interface WeatherRepo {
    suspend fun getRemoteCurrentWeather(): WeatherResponse
}