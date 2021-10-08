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
import com.example.ad340.*

class ForecastDetailsFragment : Fragment() {

    private lateinit var tempDisplaySettingManger : TempDisplaySettingManger

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_forecast_details,container,false)

        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

//        setTitle(R.string.forecast_details)

        val textTemp = view.findViewById<TextView>(R.id.temp_details)
        val textTempDescription = view.findViewById<TextView>(R.id.temp_description)

        val temp : Float = ForecastDetailsFragmentArgs.fromBundle(requireArguments()).temp
        val description : String = ForecastDetailsFragmentArgs.fromBundle(requireArguments()).description
        textTemp.text = formatTempForDisplay(temp,tempDisplaySettingManger.getTempDisplaySettings())
        textTempDescription.text = description


        return view
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.settings_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.settings -> showTempDisplaySettingDialog(this,tempDisplaySettingManger)
//        }
//        return true
//    }

}