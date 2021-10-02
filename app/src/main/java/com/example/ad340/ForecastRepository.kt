package com.example.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random


// ViewModel Class
class ForecastRepository : ViewModel(){

    // Live Data
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> get() = _weeklyForecast


    // Load Data to live data
    fun loadForecast(zipcode: String) {
        val randomValues = List(15) { Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map { randomValue ->
            DailyForecast(R.drawable.ic_cloud,String.format("%.1f", randomValue).toFloat(), "Partly Cloudy")
        }
        _weeklyForecast.value = forecastItems
    }


}