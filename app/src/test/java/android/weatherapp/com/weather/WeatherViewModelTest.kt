package android.weatherapp.com.weather

import android.weatherapp.com.weather.WeatherFactory.createWeatherListNetworkResult
import android.weatherapp.com.weather.WeatherFactory.createWeatherNetworkResult
import android.weatherapp.com.weather.di.CountryFactory.getCountryList
import android.weatherapp.com.weather.repositories.CountryRepository
import android.weatherapp.com.weather.repositories.WeatherRepository
import android.weatherapp.com.weather.ui.weather.WeatherViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {
    private val weatherRepository: WeatherRepository = mockk()
    private val countryRepository: CountryRepository = mockk()
    private val viewModel = WeatherViewModel(weatherRepository, countryRepository)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getCountries() = runBlockingTest {
        val countries = getCountryList()
        coEvery { countryRepository.getCountries() } returns countries

        val result = viewModel.getCountries()

        coVerify { countryRepository.getCountries() }
        assertEquals(result, countries)
    }

    @Test
    fun getWeathers() = runBlockingTest {
        val citiesIds = "123,123"
        val countries = createWeatherListNetworkResult()
        val flowCountries = flow {
            emit(countries)
        }

        coEvery {
            weatherRepository.getWeathersList(citiesIds)
        } returns flowCountries

        viewModel.getWeathers(citiesIds)

        coVerify { weatherRepository.getWeathersList(citiesIds) }

        val result = viewModel.weathersList.getOrAwaitValue()

        assertEquals(result, countries)
    }

    @Test
    fun getWeatherByLatLon() = runBlockingTest {
        val lat = "lat"
        val lon = "lon"

        val country = createWeatherNetworkResult()
        val flowCountry = flow {
            emit(country)
        }

        coEvery {
            weatherRepository.getWeatherByLatLon(lat, lon)
        } returns flowCountry

        viewModel.getWeatherByLatLon(lat, lon)

        coVerify { weatherRepository.getWeatherByLatLon(lat, lon) }

        val result = viewModel.singleWeather.getOrAwaitValue()

        assertEquals(result, country)
        assertTrue(result.data?.isCurrentLocation ?: false)
    }

    @Test
    fun getWeatherByCityName() = runBlockingTest {
        val cityName = "cityName"

        val country = createWeatherNetworkResult()
        val flowCountry = flow {
            emit(country)
        }

        coEvery {
            weatherRepository.getWeatherByCityName(cityName)
        } returns flowCountry

        viewModel.getWeatherByCityName(cityName)

        coVerify { weatherRepository.getWeatherByCityName(cityName) }

        val result = viewModel.singleWeather.getOrAwaitValue()

        assertEquals(result, country)
    }
}
