package dev.ch8n.weather.data.remote.model

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("data")
    val data: List<Weather>? = null
)

data class Weather(
    @SerializedName("rain")
    val rain: Int? = null,
    @SerializedName("temp")
    val temp: Int? = null,
    @SerializedName("time")
    val time: Int? = null,
    @SerializedName("wind")
    val wind: Int? = null
)