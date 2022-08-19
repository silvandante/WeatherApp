package android.weatherapp.com.weather.di

import android.weatherapp.com.weather.model.Country

object CountryFactory {

    fun getCountryList() = listOf(Country("123"), Country("321"), Country("444"))
}
