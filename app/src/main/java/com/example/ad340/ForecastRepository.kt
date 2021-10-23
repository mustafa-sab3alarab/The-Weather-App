package com.example.ad340

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ad340.api.CurrentWeather
import com.example.ad340.api.WeeklyForecast
import com.example.ad340.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// Repository
class ForecastRepository: ViewModel() {

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

}