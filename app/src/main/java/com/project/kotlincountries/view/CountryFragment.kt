package com.project.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.kotlincountries.databinding.FragmentCountryBinding
import com.project.kotlincountries.viewmodel.CountryViewModel


class CountryFragment : Fragment() {
    private var _binding : FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private var countryUid = 0
    private lateinit var viewModel : CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom()
        observeLiveData()
        arguments?.let {
            countryUid = CountryFragmentArgs.fromBundle(it).countryUid
        }
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                binding.countryName.text = country.countryName
                binding.countryCapital.text = country.countryCaptial
                binding.countryCurrency.text = country.countryCurrency
                binding.countryLanguage.text = country.countryLanguage
                binding.countryRegion.text = country.countryRegion
            }
        })
    }

}