package com.example.ad340.details

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.ad340.*

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManger : TempDisplaySettingManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingManger = TempDisplaySettingManger(this)

        setTitle(R.string.forecast_details)

        val textTemp = findViewById<TextView>(R.id.temp_details)
        val textTempDescription = findViewById<TextView>(R.id.temp_description)

        textTemp.text = formatTempForDisplay(intent.getFloatExtra(MainActivity.TEMP_TEXT, 0f),tempDisplaySettingManger.getTempDisplaySettings())
        textTempDescription.text = intent.getStringExtra(MainActivity.TEMP_DESCRIPTION)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> showTempDisplaySettingDialog(this,tempDisplaySettingManger)
        }
        return true
    }

}