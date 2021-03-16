package dev.ch8n.toiweather.data.remote.repos

import dev.ch8n.toiweather.data.remote.sources.WeatherSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


class WeatherRepository constructor(
    private val weatherSource: WeatherSource
) : WeatherRepo {

    override suspend fun getRemoteCurrentWeather(location: String) = withContext(Dispatchers.IO) {
        weatherSource.getWeatherInfo(location)
    }

}