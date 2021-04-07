package dev.ch8n.weather.di.modules

import dagger.Module
import dagger.Provides
import dev.ch8n.weather.data.remote.config.ApiManager
import dev.ch8n.weather.data.remote.config.BaseUrl
import dev.ch8n.weather.data.remote.config.NETWORK_TIMEOUT
import dev.ch8n.weather.data.remote.repos.WeatherRepo
import dev.ch8n.weather.data.remote.repos.WeatherRepository
import dev.ch8n.weather.data.remote.sources.WeatherSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object DataSourceBinder {

    @Provides
    @Singleton
    fun provideWeatherDataSource(apiManager: ApiManager): WeatherSource = WeatherSource(apiManager)

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherSource: WeatherSource): WeatherRepo =
        WeatherRepository(weatherSource)

}