package com.example.ad340

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String {
    return when (tempDisplaySetting) {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f °F", temp)
        TempDisplaySetting.Celsius -> {
            // Convert Fahrenheit (°F) to Celsius (°C)
            // (32°F − 32) × 5/9 = 0°C
            val tempC = (temp - 32) * (5f / 9f)
            String.format("%.2f °C", tempC)
        }

    }
}

fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManger: TempDisplaySettingManger) {
    AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose which temperature unit to use for temperature display")
        .setIcon(R.drawable.ic_baseline_settings_24)
        .setPositiveButton("F°") { dialogInterface, i ->
            tempDisplaySettingManger.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNegativeButton("C°") { dialogInterface, i ->
            tempDisplaySettingManger.updateSetting(TempDisplaySetting.Celsius)

        }.show()
        .setOnDismissListener {
            Toast.makeText(context, "Settings will take affect on app restart", Toast.LENGTH_SHORT)
                .show()
        }
}