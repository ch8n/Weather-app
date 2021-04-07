package dev.ch8n.weather.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.ch8n.weather.WeatherApp
import dev.ch8n.weather.di.modules.ActivityBinder
import dev.ch8n.weather.di.modules.DataSourceBinder
import dev.ch8n.weather.di.modules.NetworkBinder
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkBinder::class,
        ActivityBinder::class,
        DataSourceBinder::class
    ]
)
interface AppComponent : AndroidInjector<WeatherApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: WeatherApp): Builder

        fun build(): AppComponent
    }
}