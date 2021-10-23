package com.example.ad340.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ad340.*
import com.example.ad340.databinding.FragmentCurrentForecastBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentForecastFragment : Fragment(){

    private lateinit var viewModelForecastRepository : ForecastRepository
    private lateinit var tempDisplaySettingManger: TempDisplaySettingManger
    private lateinit var locationRepository: LocationRepository

    // View Binding
    private var _binding: FragmentCurrentForecastBinding? = null
    private val binding get() = _binding!!


    companion object {

        private const val KEY_ZIPCODE = "key_zipcode"

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        _binding = FragmentCurrentForecastBinding.inflate(inflater,container,false)
        val view = binding.root

        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        // View Model
        viewModelForecastRepository = ViewModelProvider(this)[ForecastRepository::class.java]
        viewModelForecastRepository.currentWeather.observe(viewLifecycleOwner) {

            // Text visibility
            binding.textMessage.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.locationName.visibility = View.VISIBLE
            binding.tempText.visibility = View.VISIBLE


            binding.locationName.text = it.name
            binding.tempText.text = formatTempForDisplay(it.forecast.temp,tempDisplaySettingManger.getTempDisplaySettings())
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
                is Location.Zipcode -> {
                    binding.progressBar.visibility = View.VISIBLE
                    viewModelForecastRepository.loadCurrentForecast(savedLocation.zipcode)
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}