package com.example.ad340

import com.example.ad340.api.DailyForecast

interface OnItemClickListener {
    fun onItemClick(dailyForecast: DailyForecast)
}