package android.weatherapp.com.weather

import android.weatherapp.com.weather.network.WeatherDataSource
import android.weatherapp.com.weather.network.WeatherService
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherDataSourceTest {

    private val weatherService: WeatherService = mockk(relaxed = true)
    private val weatherDataSource: WeatherDataSource = WeatherDataSource(weatherService)

    @Test
    fun getWeathersList() = runBlockingTest {
        val cityNames = "123,123"
        weatherDataSource.getWeathersList(cityNames).collect { }

        coVerify {
            weatherService.getWeathersList(cityNames)
        }
    }

    @Test
    fun getWeatherByCityName() = runBlockingTest {
        val cityName = "city_name"
        weatherDataSource.getWeatherByCityName(cityName).collect { }

        coVerify {
            weatherService.getWeatherByCityName(cityName)
        }
    }

    @Test
    fun getWeatherByLatLon() = runBlockingTest {
        val lat = "lat"
        val lon = "lon"
        weatherDataSource.getWeatherByLatLon(lat, lon).collect { }

        coVerify {
            weatherService.getWeatherByLatLon(lat, lon)
        }
    }
}
