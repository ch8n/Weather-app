package dev.ch8n.toiweather.data.remote.sources

import dev.ch8n.toiweather.BuildConfig
import dev.ch8n.toiweather.data.remote.config.AppAPI
import dev.ch8n.toiweather.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    //http://api.weatherstack.com/current?access_key={....}&query=New%20Delhi
    @GET(AppAPI.GET_WEATHER_INFO)
    suspend fun getWeatherInfo(
        @Query("access_key") accessKey: String = BuildConfig.KEY_WEATHER_API,
        @Query("query") location: String = "New Delhi"
    ): WeatherResponse
}