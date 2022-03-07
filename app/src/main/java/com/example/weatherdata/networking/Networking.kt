package com.example.weatherdata.networking

import com.example.weatherdata.Weather
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {

    val apiKey = "879caf3ab27b404cbd3372472c99bd09"
}