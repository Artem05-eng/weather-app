package com.example.weatherdata.networking

import com.example.weatherdata.Result
import com.example.weatherdata.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("current")
    fun getCity(
        @Query("city") cityName: String
    ): Call<Result<Weather>>

}