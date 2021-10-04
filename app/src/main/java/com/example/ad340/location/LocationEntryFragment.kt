package com.example.ad340.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ad340.AppNavigator
import com.example.ad340.R

class LocationEntryFragment : Fragment() {

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)


        val zipcodeEditText = view.findViewById<EditText>(R.id.zipcodeEditText)
        val enterButton = view.findViewById<Button>(R.id.enterButton)

        enterButton.setOnClickListener {
            val zipcode = zipcodeEditText.text.toString()
            if (zipcode.length != 5) {
                Toast.makeText(view.context, R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            } else {
                appNavigator.navigateCurrentForecast(zipcode)
            }
        }

        return view
    }

}