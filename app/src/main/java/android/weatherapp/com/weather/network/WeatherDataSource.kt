package android.weatherapp.com.weather.network

import javax.inject.Inject

class WeatherDataSource @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun getWeatherByCityName(cityName: String) =
        request {
            weatherService.getWeatherByCityName(cityName)
        }

    suspend fun getWeathersList(cityNames: String) =
        request {
            weatherService.getWeathersList(cityNames)
        }

    suspend fun getWeatherByLatLon(lat: String, lon: String) =
        request {
            weatherService.getWeatherByLatLon(lat, lon)
        }
}
