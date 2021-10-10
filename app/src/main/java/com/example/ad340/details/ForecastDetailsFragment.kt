package com.example.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ad340.TempDisplaySettingManger
import com.example.ad340.databinding.FragmentForecastDetailsBinding
import com.example.ad340.formatTempForDisplay

class ForecastDetailsFragment : Fragment() {

    private lateinit var tempDisplaySettingManger : TempDisplaySettingManger
    private val args : ForecastDetailsFragmentArgs by navArgs()

    private var _binding: FragmentForecastDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding  = FragmentForecastDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

        binding.tempDetails.text = formatTempForDisplay(args.temp,tempDisplaySettingManger.getTempDisplaySettings())
        binding.tempDescription.text = args.description


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}