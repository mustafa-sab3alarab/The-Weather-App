package com.example.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*
import com.example.ad340.api.DailyForecast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeeklyForecastFragment : Fragment(), OnItemClickListener {

    private lateinit var viewModelForecastRepository : ForecastRepository
    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger
    private lateinit var locationRepository: LocationRepository


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)
        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())


        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        val forecastList = view.findViewById<RecyclerView>(R.id.forecastWeeklyList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())

        viewModelForecastRepository = ViewModelProvider(this)[ForecastRepository::class.java]
        viewModelForecastRepository.weeklyForecast.observe(viewLifecycleOwner) {
            // update our list adapter
            val recyclerViewAdapter = DailyForecastAdapter(it.daily, tempDisplaySettingManger)
            forecastList.adapter = recyclerViewAdapter
            recyclerViewAdapter.onItemClickListener = this
        }

        viewModelForecastRepository.loadWeeklyForecast(zipcode)

        val fab = view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        fab.setOnClickListener {
            val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragment2ToLocationEntryFragment()
            findNavController().navigate(action)
        }

        locationRepository = LocationRepository(requireContext())
        locationRepository.savedLocation.observe(viewLifecycleOwner) { savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> viewModelForecastRepository.loadWeeklyForecast(savedLocation.zipcode)
            }
        }

        return view
    }

    override fun onItemClick(dailyForecast: DailyForecast) {
        showForecastDetails(dailyForecast)
    }


    private fun showForecastDetails(dailyForecast: DailyForecast) {

        val action =
            WeeklyForecastFragmentDirections.actionWeeklyForecastFragment2ToForecastDetailsActivity(
                dailyForecast.temp.max,
                dailyForecast.weather[0].description,
                dailyForecast.date,
                dailyForecast.weather[0].icon)
        findNavController().navigate(action)
    }

    companion object {

        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String): WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }


}