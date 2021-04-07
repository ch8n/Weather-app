package dev.ch8n.weather.data.remote.config

import dev.ch8n.weather.BuildConfig

object BaseUrl {
    private const val PROTOCOL_HTTP = BuildConfig.PROTOCOL_HTTP
    private const val BASE_URL = BuildConfig.BASE_URL
    val BASE_SERVER by lazy { "$PROTOCOL_HTTP$BASE_URL" }
}

object AppAPI {
    const val GET_WEATHER_INFO = "v2/5d3a99ed2f0000bac16ec13a"
}

const val NETWORK_TIMEOUT = 5L
