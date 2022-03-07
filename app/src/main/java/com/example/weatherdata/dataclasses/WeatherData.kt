package com.example.weatherdata

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class Result<T>(
    val data: List<T>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Weather(
    val city_name: String,
    val datetime: String,
    val temp: String
) : Parcelable


@Parcelize
@Entity(tableName = "weather", primaryKeys = ["city_name", "datetime"])
data class WeatherDatabaseClass(
    @ColumnInfo(name = "city_name")
    val city_name: String,
    @ColumnInfo(name = "datetime")
    val datetime: String,
    @ColumnInfo(name = "temp")
    val temp: String
) : Parcelable