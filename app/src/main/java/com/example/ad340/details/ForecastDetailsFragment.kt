package com.example.ad340.details

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ad340.*

class ForecastDetailsFragment : Fragment() {

    private lateinit var tempDisplaySettingManger : TempDisplaySettingManger
    private val args : ForecastDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_weekly_forecast,container,false)

        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())


        val textTemp = view.findViewById<TextView>(R.id.temp_details)
        val textTempDescription = view.findViewById<TextView>(R.id.temp_description)


        textTemp.text = formatTempForDisplay(args.temp,tempDisplaySettingManger.getTempDisplaySettings())
        textTempDescription.text = args.description


        return view
    }



}