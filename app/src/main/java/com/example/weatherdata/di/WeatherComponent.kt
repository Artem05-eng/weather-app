package com.example.weatherdata.di

import com.example.weatherdata.app.App
import com.example.weatherdata.ui.DataListFragment
import com.example.weatherdata.ui.DetailFragment
import com.example.weatherdata.ui.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, RepoModule::class])
interface WeatherComponent {
    fun inject(app: App)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: DetailFragment)
    fun inject(fragment: DataListFragment)
}