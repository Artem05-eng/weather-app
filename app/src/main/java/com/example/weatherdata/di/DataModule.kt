package com.example.weatherdata.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.weatherdata.app.App
import com.example.weatherdata.database.WeatherDao
import com.example.weatherdata.database.WeatherDatabase
import com.example.weatherdata.networking.Api
import com.example.weatherdata.networking.CustomInterceptor
import com.example.weatherdata.networking.Networking
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DataModule @Inject constructor(val app: App) {

    @Singleton
    @Provides
    fun getSharedPref(): SharedPreferences {
        return app.getSharedPreferences("SHARED_PREFERNCES", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomInterceptor())
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    @Singleton
    @Provides
    fun providesNetworkApi(okHttpClient: OkHttpClient): Api = Retrofit.Builder()
        .baseUrl("https://api.weatherbit.io/v2.0/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build().create()

    @Singleton
    @Provides
    fun providesDatabase(): WeatherDatabase = Room.databaseBuilder(
        app,
        WeatherDatabase::class.java,
        WeatherDatabase.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun providesDao(db: WeatherDatabase): WeatherDao = db.weatherDao()
}