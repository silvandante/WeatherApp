package android.weatherapp.com.weather

import android.weatherapp.com.weather.network.WeatherDataSource
import android.weatherapp.com.weather.repositories.WeatherRepository
import io.mockk.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    private val dataSource: WeatherDataSource = mockk(relaxed = true)
    private var repository: WeatherRepository = WeatherRepository(dataSource)

    @Test
    fun getWeatherList() = runBlockingTest {
        val citiesIds = "123123,123123"
        repository.getWeathersList(citiesIds).collect { }

        coVerify {
            dataSource.getWeathersList(citiesIds)
        }
    }

    @Test
    fun getWeatherByCityName() = runBlockingTest {
        val cityName = "cityname"
        repository.getWeatherByCityName(cityName).collect { }

        coVerify {
            dataSource.getWeatherByCityName(cityName)
        }
    }

    @Test
    fun getWeatherByLatLon() = runBlockingTest {
        val lat = "lat"
        val lon = "lon"
        repository.getWeatherByLatLon(lat, lon).collect { }

        coVerify {
            dataSource.getWeatherByLatLon(lat, lon)
        }
    }
}
