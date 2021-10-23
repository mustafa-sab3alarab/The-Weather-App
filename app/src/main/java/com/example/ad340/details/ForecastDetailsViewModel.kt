package com.example.ad340.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*


private val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")

class ForecastDetailsViewModel(args: ForecastDetailsFragmentArgs) : ViewModel() {


    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    init {
        _viewState.value = ForecastDetailsViewState(
            temp = args.temp,
            description = args.description,
            date = DATE_FORMAT.format(Date(args.date * 1000)),
            iconUrl = "http://openweathermap.org/img/wn/${args.image}@2x.png"
        )
    }

}