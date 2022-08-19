package android.weatherapp.com.weather.repositories

import android.weatherapp.com.weather.database.CountryDataSource
import android.weatherapp.com.weather.model.Country
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val dataSource: CountryDataSource
) {

    suspend fun getCountries(): List<Country>? {
        return dataSource.getCountries()
    }

    suspend fun insertCountry(country: Country) {
        dataSource.insertCountry(country)
    }
}
