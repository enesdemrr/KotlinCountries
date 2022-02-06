package com.project.kotlincountries.model

import com.google.gson.annotations.SerializedName

data class CountryModel (
    @SerializedName("name")
    val countryName: String,
    @SerializedName("region")
    val countryRegion: String,
    @SerializedName("capital")
    val countryCaptial: String,
    @SerializedName("currency")
    val countryCurrency: String,
    @SerializedName("language")
    val countryLanguage: String,
    @SerializedName("flag")
    val imageURL: String,
    )
