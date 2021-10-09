package com.example.ad340.forecast

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*
import com.example.ad340.details.ForecastDetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeeklyForecastFragment : Fragment(), OnItemClickListener {

    private var viewModelForecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

//        val zipcode = CurrentForecastFragmentArgs.fromBundle(requireArguments()).zipcode

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
//            val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragment2ToLocationEntryFragment()
//            findNavController().navigate(action)
            view.findNavController().navigate(R.id.action_weeklyForecastFragment2_to_locationEntryFragment)
        }
        return view
    }

    override fun onItemClick(dailyForecast: DailyForecast) {
        showForecastDetails(dailyForecast)
    }


    private fun showForecastDetails(dailyForecast: DailyForecast) {

        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragment2ToForecastDetailsActivity(dailyForecast.temp,dailyForecast.description)
        view?.findNavController()?.navigate(action)

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