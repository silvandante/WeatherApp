package android.weatherapp.com.weather.database

import android.weatherapp.com.weather.model.Country
import javax.inject.Inject

class CountryDataSource @Inject constructor(
    private val countriesDao: CountryDao
) {
    suspend fun insertCountry(country: Country) =
        countriesDao.insertCountry(country)

    suspend fun getCountries(): List<Country>? =
        countriesDao.getCountries()
}
