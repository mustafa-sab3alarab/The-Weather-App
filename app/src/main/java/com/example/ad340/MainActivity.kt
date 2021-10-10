package com.example.ad340

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        tempDisplaySettingManger = TempDisplaySettingManger(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        setSupportActionBar(toolbar)


        val navController = findNavController(R.id.fragment_nav_host)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
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

    private fun hasAccessLocation(): Boolean {
        return ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        val permission = mutableListOf<String>()
        if (!hasAccessLocation())
        {
            permission.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (permission.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permission.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty())
        {
            for (i in grantResults.indices)
            {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("PermissionsRequest","${permissions[i]} granted")
                }
            }
        }
    }
}