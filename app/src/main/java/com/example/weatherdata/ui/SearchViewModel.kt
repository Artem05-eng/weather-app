package com.example.weatherdata.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdata.SingleLiveEvent
import com.example.weatherdata.Weather
import com.example.weatherdata.repositories.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: Repo): ViewModel() {

    private val mutableResult = SingleLiveEvent<Weather>()

    val result: LiveData<Weather>
        get() = mutableResult

    fun getCityList(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getWeatherFromApi(name) ?: error("No response")
                mutableResult.postValue(result.first())
            } catch (t: Throwable) {
                Log.e("Server", t.message, t)

            }
        }
    }

    fun saveDataToDB(result: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveDataToDB(result)
        }
    }

    fun putDataFromPrefs(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putDataToSharedPref(name)
        }
    }

    suspend fun getAllDataFromPref(): List<String> {
        return repository.getAllDataFromSharedPref()
    }
}