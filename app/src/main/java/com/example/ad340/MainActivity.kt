package com.example.ad340

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){

    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger

//    companion object {
//        const val TEMP_TEXT: String = "TEMP_TEXT"
//        const val TEMP_DESCRIPTION: String = "TEMP_DESCRIPTION"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tempDisplaySettingManger = TempDisplaySettingManger(this)


//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.fragment_container,LocationEntryFragment())
//            .commit()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> showTempDisplaySettingDialog(this, tempDisplaySettingManger)
        }
        return true
    }



//    override fun navigateCurrentForecast(zipcode: String) {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragment_container,CurrentForecastFragment.newInstance(zipcode))
//            .commit()
//    }
//
//    override fun navigateToLocationEntry() {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragment_container,LocationEntryFragment())
//            .commit()
//    }
}