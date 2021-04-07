package dev.ch8n.weather.data.remote.config

import dev.ch8n.weather.data.remote.sources.WeatherService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class ApiManager constructor(private val retrofit: Retrofit) {
    val weatherServices: WeatherService by lazy { retrofit.api() }
}

private inline fun <reified T> Retrofit.api(): T {
    return create(T::class.java)
}