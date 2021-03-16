package dev.ch8n.toiweather.data.remote.model


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: Current? = null,
    @SerializedName("location")
    val location: Location? = null
) {
    companion object {
        fun fake() = WeatherResponse(
            current = Current.fake(),
            location = Location.fake()
        )
    }
}

data class Location(
    @SerializedName("name")
    val name: String? = null,
) {
    companion object {
        fun fake() = Location(name = "delhi")
    }
}

data class Current(
    @SerializedName("cloudcover")
    val cloudcover: Int? = null,
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("precip")
    val precip: Int? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("temperature")
    val temperature: Int? = null,
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>? = null,
    @SerializedName("wind_speed")
    val windSpeed: Int? = null
) {
    companion object {
        fun fake() = Current(
            cloudcover = 10,
            precip = 10,
            pressure = 10,
            temperature = 10,
            windSpeed = 10,
            weatherDescriptions = listOf("rain")
        )
    }
}