package dev.ch8n.toiweather

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

import dev.ch8n.toiweather.di.DaggerAppComponent

class TOIApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent
        .builder()
        .application(this)
        .build()

}
