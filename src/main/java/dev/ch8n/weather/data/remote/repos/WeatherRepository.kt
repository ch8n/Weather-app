package dev.ch8n.weather.data.remote.repos

import dev.ch8n.weather.data.remote.sources.WeatherSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


class WeatherRepository constructor(
    private val weatherSource: WeatherSource
) : WeatherRepo {

    override suspend fun getRemoteCurrentWeather() = withContext(Dispatchers.IO) {
        weatherSource.getWeatherInfo()
    }

}