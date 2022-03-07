package com.example.weatherdata.database

import androidx.room.*
import com.example.weatherdata.WeatherDatabaseClass

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(result: WeatherDatabaseClass)

    @Query("SELECT * FROM weather")
    suspend fun getAllData(): List<WeatherDatabaseClass>

    @Query("SELECT * FROM weather WHERE city_name = :name")
    suspend fun getData(name: String): List<WeatherDatabaseClass>

    @Query("DELETE FROM weather WHERE city_name = :name")
    suspend fun clearData(name: String)
}