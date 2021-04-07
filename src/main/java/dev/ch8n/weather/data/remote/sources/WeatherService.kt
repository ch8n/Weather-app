package dev.ch8n.weather.data.remote.sources

import dev.ch8n.weather.data.remote.config.AppAPI
import dev.ch8n.weather.data.remote.model.WeatherResponse
import retrofit2.http.GET

interface WeatherService {

    //https://www.mocky.io/v2/5d3a99ed2f0000bac16ec13a
    @GET(AppAPI.GET_WEATHER_INFO)
    suspend fun getWeatherInfo(): WeatherResponse
}