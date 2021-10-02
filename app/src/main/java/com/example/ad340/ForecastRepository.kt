package com.example.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random


// ViewModel Class
class ForecastRepository : ViewModel() {

    // Live Data
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> get() = _weeklyForecast


    // Load Data to live data
    fun loadForecast(zipcode: String) {
        val randomValues = List(15) { Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map { randomTempValue ->
            DailyForecast(getImage(randomTempValue), randomTempValue, getTempDescription(randomTempValue))
        }
        _weeklyForecast.value = forecastItems
    }

    private fun getTempDescription(temp: Float): String {
        return when(temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder than I would prefer"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That's the sweet spot!"
            in 80f.rangeTo(90f) -> "Getting a little warm"
            in 90f.rangeTo(100f) -> "Where's the A/C?"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What is this, Arizona?"
            else -> "Does not compute"
        }
    }

    private fun getImage(temp: Float): Int {
        return when(temp){
            in Float.MIN_VALUE.rangeTo(0f) -> 0
            in 0f.rangeTo(32f) -> R.drawable.ic_cold
            in 32f.rangeTo(55f) -> R.drawable.ic_cold
            in 55f.rangeTo(65f) -> R.drawable.ic_cold
            in 65f.rangeTo(80f) -> R.drawable.ic_sun_copy
            in 80f.rangeTo(90f) ->  R.drawable.ic_sun_copy
            in 90f.rangeTo(100f) -> R.drawable.ic_sun_copy
            in 100f.rangeTo(Float.MAX_VALUE) -> 0
            else -> 0
        }
    }


}