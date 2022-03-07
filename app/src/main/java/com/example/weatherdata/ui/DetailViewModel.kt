package com.example.weatherdata.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdata.Weather
import com.example.weatherdata.repositories.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: Repo) : ViewModel() {

    private val mutableList = MutableLiveData<List<Weather>>()

    val list : LiveData<List<Weather>>
        get() = mutableList

    fun deleteData(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearDataFromDB(name)
        }
    }

    fun getDataFromDB(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getDataFromDB(name)
            mutableList.postValue(data)
        }
    }

    fun deleteFromSharedPref(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeDataFromSharedPref(name)
        }
    }
}