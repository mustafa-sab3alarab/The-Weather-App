package com.example.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.ad340.TempDisplaySettingManger
import com.example.ad340.databinding.FragmentForecastDetailsBinding
import com.example.ad340.formatTempForDisplay
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")

class ForecastDetailsFragment : Fragment() {


    private lateinit var tempDisplaySettingManger : TempDisplaySettingManger
    private val args : ForecastDetailsFragmentArgs by navArgs()

    // View Binding
    private var _binding: FragmentForecastDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding  = FragmentForecastDetailsBinding.inflate(inflater,container,false)
        val view = binding.root

        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

        binding.tempDetails.text = formatTempForDisplay(args.temp,tempDisplaySettingManger.getTempDisplaySettings())
        binding.tempDescriptionDetails.text = args.description
        binding.dateDetails.text = DATE_FORMAT.format(Date(args.date * 1000))
        binding.iconTempDetails.load("http://openweathermap.org/img/wn/${args.image}@2x.png")


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}