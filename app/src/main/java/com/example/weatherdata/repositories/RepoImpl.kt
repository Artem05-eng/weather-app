package com.example.weatherdata.repositories

import android.content.SharedPreferences
import com.example.weatherdata.Weather
import com.example.weatherdata.database.WeatherDao
import com.example.weatherdata.networking.Api
import com.example.weatherdata.weatherDatabaseClassToWeather
import com.example.weatherdata.weatherToWeatherDatabaseClass
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val network: Api,
    private val dao: WeatherDao,
    private val sharedPref: SharedPreferences
): Repo {

    override fun getWeatherFromApi(cityName: String): List<Weather>? {
        val response = network.getCity(cityName).execute()
        if (response.isSuccessful) {
            return response.body()?.data
        } else {
            error("Connection server error!")
        }
    }

    override suspend fun getAllDataFromDB(): List<Weather> {
        return dao.getAllData().map { weatherDatabaseClassToWeather(it) }
    }

    override suspend fun getDataFromDB(name: String): List<Weather> {
        return dao.getData(name).map { weatherDatabaseClassToWeather(it) }
    }

    override suspend fun saveDataToDB(result: Weather) {
        dao.insertData(weatherToWeatherDatabaseClass(result))
    }

    override suspend fun clearDataFromDB(name: String) {
        dao.clearData(name)
    }

    override fun removeDataFromSharedPref(name: String) {
        sharedPref.edit().remove(name).commit()
    }

    override suspend fun getAllDataFromSharedPref(): List<String> {
        return sharedPref.all.keys.toList()
    }

    override fun putDataToSharedPref(name: String) {
        sharedPref.edit().putString(name, name).commit()
    }
}