package com.example.weatherdata.app

import android.app.Application
import android.content.SharedPreferences
import com.example.weatherdata.database.WeatherDatabase
import com.example.weatherdata.di.DaggerWeatherComponent
import com.example.weatherdata.di.DataModule
import com.example.weatherdata.di.WeatherComponent
import javax.inject.Inject

class App : Application() {

    @Inject lateinit var component: WeatherComponent
    @Inject lateinit var database: WeatherDatabase
    @Inject lateinit var sharedPref: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        component = DaggerWeatherComponent.builder().dataModule(DataModule(this)).build()
        component.inject(this)
    }
}