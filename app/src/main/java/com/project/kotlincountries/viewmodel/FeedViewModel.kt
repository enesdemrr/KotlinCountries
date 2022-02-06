package com.project.kotlincountries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincountries.model.CountryModel

class FeedViewModel : ViewModel() {

    val countries = MutableLiveData<List<CountryModel>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){

        val country = CountryModel("Turkey","Asia","Ankara","TL","Turkish","wwww.ww.com")
        val country2 = CountryModel("England","Europe","Londan","EUR","English","wwww.ww.com")
        val country3 = CountryModel("Italy","Europe","Roma","EUR","Italian","wwww.ww.com")

        val countryList = arrayListOf<CountryModel>(country,country2,country3)
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }
}