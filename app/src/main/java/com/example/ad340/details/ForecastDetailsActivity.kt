package com.example.ad340.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.ad340.MainActivity
import com.example.ad340.R

class ForecastDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        setTitle(R.string.forecast_details)

        val textTemp = findViewById<TextView>(R.id.temp_details)
        val textTempDescription = findViewById<TextView>(R.id.temp_description)

        textTemp.text = intent.getStringExtra(MainActivity.TEMP_TEXT)
        textTempDescription.text = intent.getStringExtra(MainActivity.TEMP_DESCRIPTION)
    }
}