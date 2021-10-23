package com.example.ad340.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ForecastDetailsFragmentFactory(private val args: ForecastDetailsFragmentArgs) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java)){
            return ForecastDetailsViewModel(args) as T
        }
        throw IllegalArgumentException("Unknown ")
    }
}