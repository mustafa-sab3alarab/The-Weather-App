package com.example.ad340.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.ad340.Location
import com.example.ad340.LocationRepository
import com.example.ad340.R
import com.example.ad340.databinding.FragmentLocationEntryBinding

class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository

    // View Binding
    private var _binding : FragmentLocationEntryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLocationEntryBinding.inflate(inflater,container,false)
        val view = binding.root
        locationRepository = LocationRepository(requireContext())

        binding.enterButton.setOnClickListener {
            val zipcode = binding.zipcodeEditText.text.toString()
            if (zipcode.length != 5) {
                Toast.makeText(view.context, R.string.zipcode_entry_error, Toast.LENGTH_SHORT)
                    .show()
            } else {
                locationRepository.saveLocation(Location.Zipcode(zipcode))
                findNavController().navigateUp()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}