package com.project.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincountries.model.CountryModel
import com.project.kotlincountries.service.CountryAPIService
import com.project.kotlincountries.service.CountryDatabase
import com.project.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 *1000 *1000 *1000L
    val countries = MutableLiveData<List<CountryModel>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }
    }

    private fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from SQLite",Toast.LENGTH_SHORT).show()
        }
    }
    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        countryLoading.value =true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryModel>>(){
                    override fun onSuccess(t: List<CountryModel>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList : List<CountryModel>){

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list : List<CountryModel>){
        countryLoading.value = true
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.getAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i<list.size){
                list[i].uuid = listLong[i].toInt()
                i += 1
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}