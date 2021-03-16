package dev.ch8n.toiweather.di.modules

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dev.ch8n.toiweather.data.remote.config.ApiManager
import dev.ch8n.toiweather.data.remote.config.BaseUrl
import dev.ch8n.toiweather.data.remote.config.NETWORK_TIMEOUT
import dev.ch8n.toiweather.ui.MainActivity
import dev.ch8n.toiweather.ui.di.MainDI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class ActivityBinder {

    @ContributesAndroidInjector(modules = [MainDI::class])
    abstract fun bindMainActivity(): MainActivity


}