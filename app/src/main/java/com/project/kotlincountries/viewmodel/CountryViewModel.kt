package com.project.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincountries.model.CountryModel

class CountryViewModel : ViewModel() {
    val countryLiveData = MutableLiveData<CountryModel>()
    fun getDataFromRoom(){
        val country = CountryModel("Turkey","Asia","Ankara","TL","Turkish","wwww.ww.com")
        countryLiveData.value= country
    }
}