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
import com.example.ad340.databinding.FragmentWeeklyForecastBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WeeklyForecastFragment : Fragment(), OnItemClickListener {

    private var forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger
    private lateinit var locationRepository: LocationRepository


    // View Binding
    private var _binding : FragmentWeeklyForecastBinding? = null
    private val binding get() = _binding!!


    companion object {

        const val KEY_ZIPCODE = "key_zipcode"

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentWeeklyForecastBinding.inflate(inflater,container,false)
        val view = binding.root

        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())


        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        val forecastList = view.findViewById<RecyclerView>(R.id.forecastWeeklyList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())



        forecastRepository.weeklyForecast.observe(viewLifecycleOwner) {

            binding.textMessage.visibility = View.GONE
            binding.progressBar.visibility = View.GONE

            // update our list adapter
            val recyclerViewAdapter = DailyForecastAdapter(it.daily, tempDisplaySettingManger)
            forecastList.adapter = recyclerViewAdapter
            recyclerViewAdapter.onItemClickListener = this
        }

        forecastRepository.loadWeeklyForecast(zipcode)

        val fab = view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        fab.setOnClickListener {
            val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragment2ToLocationEntryFragment()
            findNavController().navigate(action)
        }

        locationRepository = LocationRepository(requireContext())
        locationRepository.savedLocation.observe(viewLifecycleOwner) { savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> {
                    binding.progressBar.visibility = View.VISIBLE
                    forecastRepository.loadWeeklyForecast(savedLocation.zipcode)
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}