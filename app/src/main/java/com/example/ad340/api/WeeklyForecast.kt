package com.example.ad340.api

import com.squareup.moshi.Json

data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

data class Temp(val min: Float, val max: Float)

data class DailyForecast(
    @field:Json(name = "dt") val date: Long,
    val temp: Temp,
    val weather: List<WeatherDescription>,
    val image : String
)

data class WeeklyForecast(val daily: List<DailyForecast>)