package com.example.ad340.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*
import com.example.ad340.details.ForecastDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment(), OnItemClickListener {

    private var viewModelForecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger
    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        val forecastList = view.findViewById<RecyclerView>(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        viewModelForecastRepository.weeklyForecast.observe(viewLifecycleOwner) {
            // update our list adapter
            val recyclerViewAdapter = DailyForecastAdapter(it, tempDisplaySettingManger)
            forecastList.adapter = recyclerViewAdapter
            recyclerViewAdapter.onItemClickListener = this
        }

        viewModelForecastRepository.loadForecast(zipcode)
        val fab = view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        fab.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }
        return view
    }

    override fun onItemClick(dailyForecast: DailyForecast) {
        showForecastDetails(dailyForecast)
    }


    private fun showForecastDetails(dailyForecast: DailyForecast) {
        Intent(requireContext(), ForecastDetailsActivity::class.java).also {
            it.putExtra(MainActivity.TEMP_TEXT, dailyForecast.temp)
            it.putExtra(MainActivity.TEMP_DESCRIPTION, dailyForecast.description)
            startActivity(it)
        }
    }

    companion object {

        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE,zipcode)
            fragment.arguments = args

            return fragment
        }
    }


}