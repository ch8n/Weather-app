package dev.ch8n.toiweather.data.remote.model


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: Current? = null,
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("request")
    val request: Request? = null
)

data class Location(
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("lat")
    val lat: String? = null,
    @SerializedName("localtime")
    val localtime: String? = null,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int? = null,
    @SerializedName("lon")
    val lon: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("timezone_id")
    val timezoneId: String? = null,
    @SerializedName("utc_offset")
    val utcOffset: String? = null
)

data class Request(
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("query")
    val query: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("unit")
    val unit: String? = null
)

data class Current(
    @SerializedName("cloudcover")
    val cloudcover: Int? = null,
    @SerializedName("feelslike")
    val feelslike: Int? = null,
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("is_day")
    val isDay: String? = null,
    @SerializedName("observation_time")
    val observationTime: String? = null,
    @SerializedName("precip")
    val precip: Int? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("temperature")
    val temperature: Int? = null,
    @SerializedName("uv_index")
    val uvIndex: Int? = null,
    @SerializedName("visibility")
    val visibility: Int? = null,
    @SerializedName("weather_code")
    val weatherCode: Int? = null,
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>? = null,
    @SerializedName("weather_icons")
    val weatherIcons: List<String>? = null,
    @SerializedName("wind_degree")
    val windDegree: Int? = null,
    @SerializedName("wind_dir")
    val windDir: String? = null,
    @SerializedName("wind_speed")
    val windSpeed: Int? = null
)