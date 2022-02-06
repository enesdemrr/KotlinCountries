package com.project.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.kotlincountries.model.CountryModel
@Dao
interface CountryDao {
    @Insert
    suspend fun insertAll(vararg countries : CountryModel) : List<Long>

    @Query("SELECT * FROM countrymodel")
    suspend fun getAllCountries() : List<CountryModel>

    @Query("SELECT * FROM countrymodel WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : CountryModel

    @Query("DELETE FROM countrymodel")
    suspend fun deleteAllCountries()
}