package com.example.weatherdata.repositories

import com.example.weatherdata.Weather

interface Repo {

    fun getWeatherFromApi(cityName: String): List<Weather>?

    suspend fun getAllDataFromDB(): List<Weather>

    suspend fun getDataFromDB(name: String): List<Weather>

    suspend fun saveDataToDB(result: Weather)

    suspend fun clearDataFromDB(name: String)

    fun removeDataFromSharedPref(name: String)

    suspend fun getAllDataFromSharedPref(): List<String>

    fun putDataToSharedPref(name: String)
}