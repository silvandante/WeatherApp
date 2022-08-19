package android.weatherapp.com.weather.repositories

import android.weatherapp.com.weather.network.WeatherDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val dataSource: WeatherDataSource
) {
    suspend fun getWeatherByCityName(cityName: String) =
        dataSource.getWeatherByCityName(cityName)

    suspend fun getWeathersList(citiesIds: String) =
        dataSource.getWeathersList(citiesIds)

    suspend fun getWeatherByLatLon(lat: String, lon: String) =
        dataSource.getWeatherByLatLon(lat, lon)
}
