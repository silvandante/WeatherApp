package android.weatherapp.com.weather

import android.weatherapp.com.weather.database.CountryDataSource
import android.weatherapp.com.weather.di.CountryFactory
import android.weatherapp.com.weather.repositories.CountryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class CountryRepositoryTest {

    private val dataSource: CountryDataSource = mockk(relaxed = true)
    private val repository = CountryRepository(dataSource)

    @Test
    fun insertCountry() = runBlockingTest {
        val country0 = CountryFactory.getCountryList()[0]
        repository.insertCountry(country0)

        val country1 = CountryFactory.getCountryList()[1]
        coVerify { dataSource.insertCountry(country0) }
        coVerify(exactly = 0) { dataSource.insertCountry(country1) }
    }

    @Test
    fun getCountries() = runBlockingTest {
        val countries = CountryFactory.getCountryList()
        coEvery { dataSource.getCountries() } returns countries

        val result = repository.getCountries()

        coVerify { dataSource.getCountries() }
        Assert.assertEquals(result, countries)
    }
}
