package dev.ch8n.toiweather.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dev.ch8n.toiweather.data.remote.repos.WeatherRepo
import dev.ch8n.toiweather.data.remote.repos.WeatherRepository
import dev.ch8n.toiweather.data.remote.sources.WeatherSource
import dev.ch8n.toiweather.ui.MainActivity
import dev.ch8n.toiweather.ui.MainViewModel
import javax.inject.Inject

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

class MainViewModelFactory @Inject constructor(
    private val weatherRepo: WeatherRepo
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(weatherRepo) as T
    }
}