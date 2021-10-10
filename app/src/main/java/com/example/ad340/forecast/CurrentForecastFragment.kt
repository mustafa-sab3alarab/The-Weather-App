package com.example.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ad340.*
import com.example.ad340.api.DailyForecast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment(), OnItemClickListener {

    private var viewModelForecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger
    private lateinit var locationRepository: LocationRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        val locationName = view.findViewById<TextView>(R.id.location_name)
        val tempText = view.findViewById<TextView>(R.id.temp_text)

        viewModelForecastRepository.currentWeather.observe(viewLifecycleOwner) {
            locationName.text = it.name
            tempText.text = formatTempForDisplay(it.forecast.temp,tempDisplaySettingManger.getTempDisplaySettings())

        }

        viewModelForecastRepository.loadCurrentForecast(zipcode)

        val fab = view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        fab.setOnClickListener {
            val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
            findNavController().navigate(action)
        }

        locationRepository = LocationRepository(requireContext())
        locationRepository.savedLocation.observe(viewLifecycleOwner){savedLocation  ->
            when(savedLocation){
                is Location.Zipcode -> viewModelForecastRepository.loadCurrentForecast(savedLocation.zipcode)
            }
        }
        return view
    }

    override fun onItemClick(dailyForecast: DailyForecast) {
//        showForecastDetails(dailyForecast)
    }

//
//    private fun showForecastDetails(dailyForecast: DailyForecast) {
//        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsActivity(dailyForecast.temp.max,dailyForecast.weather[0].description)
//        findNavController().navigate(action)
//
//    }

    companion object {

        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }


}