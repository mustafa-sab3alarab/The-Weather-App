package com.example.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment(), OnItemClickListener {

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
            view.findNavController().navigate(R.id.action_currentForecastFragment_to_locationEntryFragment)
        }
        return view
    }

    override fun onItemClick(dailyForecast: DailyForecast) {
        showForecastDetails(dailyForecast)
    }


    private fun showForecastDetails(dailyForecast: DailyForecast) {

        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsActivity(dailyForecast.temp,dailyForecast.description)
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