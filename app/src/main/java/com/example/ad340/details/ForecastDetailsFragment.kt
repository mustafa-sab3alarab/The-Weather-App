package com.example.ad340.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.ad340.TempDisplaySettingManger
import com.example.ad340.databinding.FragmentForecastDetailsBinding
import com.example.ad340.formatTempForDisplay
import java.text.SimpleDateFormat
import java.util.*


class ForecastDetailsFragment : Fragment() {


    private lateinit var tempDisplaySettingManger : TempDisplaySettingManger
    private val args : ForecastDetailsFragmentArgs by navArgs()
    private lateinit var viewModel: ForecastDetailsViewModel

    // View Binding
    private var _binding: FragmentForecastDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding  = FragmentForecastDetailsBinding.inflate(inflater,container,false)
        val view = binding.root

        tempDisplaySettingManger = TempDisplaySettingManger(requireContext())

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,ForecastDetailsFragmentFactory(args))[ForecastDetailsViewModel::class.java]
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            // Update the UI
            binding.tempDetails.text = formatTempForDisplay(viewState.temp,tempDisplaySettingManger.getTempDisplaySettings())
            binding.tempDescriptionDetails.text = viewState.description
            binding.dateDetails.text = viewState.date
            binding.iconTempDetails.load(viewState.iconUrl)
        }
    }

}