package com.example.weatherdata.di

import com.example.weatherdata.repositories.Repo
import com.example.weatherdata.repositories.RepoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepoModule {

    @Binds
    abstract fun providesRepository(repo: RepoImpl): Repo
}