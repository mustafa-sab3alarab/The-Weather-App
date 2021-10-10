package com.example.ad340

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ad340.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.random.Random


// ViewModel Class
class ForecastRepository : ViewModel() {

    // Live Data
    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> get() = _currentWeather

    // Live Data
    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast: LiveData<WeeklyForecast> get() = _weeklyForecast


    // Load Data to live data
    fun loadWeeklyForecast(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode,"imperial",BuildConfig.OPEN_WEATHER_MAP_API_KEY)

        call.enqueue(object:Callback<CurrentWeather>{

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {

                val weatherResponse = response.body()
                if (weatherResponse != null){
                    // load 7 day forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )

                    forecastCall.enqueue(object :Callback<WeeklyForecast>{
                        override fun onResponse(call: Call<WeeklyForecast>, response: Response<WeeklyForecast>) {

                            val weeklyForecastResponse = response.body()
                            if (weeklyForecastResponse != null)
                                _weeklyForecast.value = weeklyForecastResponse
                        }

                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName,"error loading location for weekly forecast",t)
                        }
                    })
                }


            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName,"error loading location for weekly forecast",t)
            }
        })
    }

    fun loadCurrentForecast(zipcode: String) {
        val call = createOpenWeatherMapService().currentWeather(zipcode,"imperial",BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object:Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null)
                    _currentWeather.value = weatherResponse
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName,"error loading current weather",t)
            }
        })
    }

    private fun getTempDescription(temp: Float): String {
        return when (temp) {
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
        return when (temp) {
            in 0f.rangeTo(32f) -> R.drawable.ic_cold
            in 32f.rangeTo(55f) -> R.drawable.ic_cold
            in 55f.rangeTo(65f) -> R.drawable.ic_cold
            in 65f.rangeTo(80f) -> R.drawable.ic_sun_copy
            in 80f.rangeTo(90f) -> R.drawable.ic_sun_copy
            in 90f.rangeTo(100f) -> R.drawable.ic_sun_copy
            else -> -1
        }
    }


}