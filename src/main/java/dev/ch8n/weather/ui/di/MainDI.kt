package dev.ch8n.weather.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dev.ch8n.weather.data.remote.repos.WeatherRepo
import dev.ch8n.weather.data.remote.repos.WeatherRepository
import dev.ch8n.weather.data.remote.sources.WeatherSource
import dev.ch8n.weather.ui.MainActivity
import dev.ch8n.weather.ui.MainViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Module
object MainDI {

    @Provides
    fun provideWeatherRepo(weatherSource: WeatherSource): WeatherRepo = WeatherRepository(weatherSource)

    @Provides
    fun provideMainViewModel(
        mainActivity: MainActivity,
        factory: MainViewModelFactory
    ): MainViewModel = ViewModelProvider(mainActivity, factory)
        .get(MainViewModel::class.java)

}

@Singleton
class MainViewModelFactory @Inject constructor(
    private val weatherRepo: WeatherRepo
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(weatherRepo) as T
    }
}