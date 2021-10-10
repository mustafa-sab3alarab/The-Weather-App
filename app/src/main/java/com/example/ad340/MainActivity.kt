package com.example.ad340

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.ad340.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        requestPermission()

        tempDisplaySettingManger = TempDisplaySettingManger(this)

        setSupportActionBar(binding.toolbarMain)


        val navController = findNavController(R.id.fragment_nav_host)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarMain.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

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