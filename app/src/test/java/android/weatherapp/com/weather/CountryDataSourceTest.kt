package android.weatherapp.com.weather

import android.weatherapp.com.weather.database.CountryDao
import android.weatherapp.com.weather.database.CountryDataSource
import android.weatherapp.com.weather.di.CountryFactory.getCountryList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class CountryDataSourceTest {

    private val countryDao: CountryDao = mockk(relaxed = true)
    private val countryDataSource = CountryDataSource(countryDao)

    @Test
    fun insertCountry() = runBlockingTest {
        val country0 = getCountryList()[0]
        countryDataSource.insertCountry(country0)

        val country1 = getCountryList()[1]
        coVerify(exactly = 1) { countryDao.insertCountry(country0) }
        coVerify(exactly = 0) { countryDao.insertCountry(country1) }
    }

    @Test
    fun getCountries() = runBlockingTest {
        val countries = getCountryList()
        coEvery { countryDao.getCountries() } returns countries

        val result = countryDataSource.getCountries()

        coVerify { countryDao.getCountries() }
        assertEquals(result, countries)
    }
}
