package com.example.weatherdata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherdata.WeatherDatabaseClass

@Database(entities = [WeatherDatabaseClass::class], version = WeatherDatabase.DB_VERSION)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "weather-database"
    }
}