package com.example.weatherdata

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun weatherToWeatherDatabaseClass(weather: Weather): WeatherDatabaseClass {
    return WeatherDatabaseClass(
        city_name = weather.city_name,
        datetime = weather.datetime,
        temp = weather.temp
    )
}

fun weatherDatabaseClassToWeather(weatherDatabaseClass: WeatherDatabaseClass): Weather {
    return Weather(
        city_name = weatherDatabaseClass.city_name,
        datetime = weatherDatabaseClass.datetime,
        temp = weatherDatabaseClass.temp
    )
}

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
        val args = Bundle().apply(action)
        arguments = args
    }
}