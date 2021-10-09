package com.example.ad340

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManger = TempDisplaySettingManger(this)

        val toolbar : Toolbar = findViewById(R.id.toolbar_main)
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)

        setSupportActionBar(toolbar)


        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

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
}