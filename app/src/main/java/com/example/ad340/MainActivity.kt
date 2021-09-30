package com.example.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Block Screen Shoot
        //window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE)

        val zipcodeEditText = findViewById<EditText>(R.id.zipcodeEditText)
        val enterButton = findViewById<Button>(R.id.enterButton)

        enterButton.setOnClickListener {
            val zipcode = zipcodeEditText.text.toString()
            if (zipcode.length != 5) {
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, zipcode, Toast.LENGTH_SHORT).show()
        }
    }
}