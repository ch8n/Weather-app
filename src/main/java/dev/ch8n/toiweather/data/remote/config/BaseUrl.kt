package dev.ch8n.toiweather.data.remote.config

import dev.ch8n.toiweather.BuildConfig

object BaseUrl {
    private const val PROTOCOL_HTTP = BuildConfig.PROTOCOL_HTTP
    private const val BASE_URL = BuildConfig.BASE_URL
    val BASE_SERVER by lazy { "$PROTOCOL_HTTP$BASE_URL" }
}

object AppAPI {
    const val GET_WEATHER_INFO = "/current"
}

const val NETWORK_TIMEOUT = 5L
