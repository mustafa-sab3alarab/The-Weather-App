package com.example.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random


// ViewModel
class ForecastRepository : ViewModel(){
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()

    val weeklyForecast: LiveData<List<DailyForecast>> get() = _weeklyForecast

    fun loadForecast(zipcode: String) {
        val randomValues = List(7) { Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map { randomValue ->
            DailyForecast(randomValue, "Partly Cloudy")
        }
        _weeklyForecast.value = forecastItems
    }


}