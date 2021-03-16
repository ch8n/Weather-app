package dev.ch8n.toiweather.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dev.ch8n.toiweather.TOIApp
import dev.ch8n.toiweather.di.modules.ActivityBinder
import dev.ch8n.toiweather.di.modules.NetworkBinder
import javax.inject.Singleton


@Singleton
@Component(
    modules = arrayOf(
        AndroidSupportInjectionModule::class,
        NetworkBinder::class,
        ActivityBinder::class
    )
)
interface AppComponent : AndroidInjector<TOIApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TOIApp): Builder

        fun build(): AppComponent
    }
}