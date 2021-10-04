package com.example.ad340

import android.content.Context

enum class TempDisplaySetting {
    Fahrenheit, Celsius
}

class TempDisplaySettingManger(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    fun updateSetting(setting: TempDisplaySetting) {
        preferences.edit().putString("key_temp_display", setting.name).apply()
    }

    fun getTempDisplaySettings(): TempDisplaySetting {
        val settingValue = preferences.getString("key_temp_display", TempDisplaySetting.Fahrenheit.name)  ?: TempDisplaySetting.Fahrenheit.name
        return TempDisplaySetting.valueOf(settingValue)
    }
}