package dev.ch8n.weather

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

import dev.ch8n.weather.di.DaggerAppComponent

class WeatherApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent
        .builder()
        .application(this)
        .build()

}
