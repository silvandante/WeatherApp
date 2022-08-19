package android.weatherapp.com.weather

import android.weatherapp.com.weather.model.Clouds
import android.weatherapp.com.weather.model.Coord
import android.weatherapp.com.weather.model.Main
import android.weatherapp.com.weather.model.Sys
import android.weatherapp.com.weather.model.Weather
import android.weatherapp.com.weather.model.WeatherObject
import android.weatherapp.com.weather.model.Weathers
import android.weatherapp.com.weather.model.Wind
import android.weatherapp.com.weather.network.NetworkResult

object WeatherFactory {
    fun createWeatherListNetworkResult(): NetworkResult<Weathers> {
        return NetworkResult.Success(
            createWeatherList
        )
    }

    fun createWeatherNetworkResult(): NetworkResult<WeatherObject> {
        return NetworkResult.Success(
            createWeatherList.list[0]
        )
    }

    val createWeatherList = Weathers(
        cnt = 0,
        list = listOf(
            WeatherObject(
                isCurrentLocation = true, clouds = Clouds(all = 0), coord = Coord(lat = 38.7167, lon = -9.1333), dt = 1660759785, id = 2267057, main = Main(feels_like = 21.21, grnd_level = 0, humidity = 50, pressure = 1016, sea_level = 0, temp = 21.68, temp_max = 22.68, temp_min = 20.86), name = "Lisbon", sys = Sys(country = "PT", sunrise = 1660715531, sunset = 1660764563), visibility = 10000, weather = listOf(Weather(description = "clear sky", icon = "01d", id = 800, main = "Clear")), wind = Wind(deg = 360, speed = 13.89), timezone = 0
            ),
            WeatherObject(
                isCurrentLocation = false, clouds = Clouds(all = 40), coord = Coord(lat = 40.4165, lon = -3.7026), dt = 1660759779, id = 3117735, main = Main(feels_like = 24.55, grnd_level = 0, humidity = 31, pressure = 1013, sea_level = 0, temp = 25.17, temp_max = 26.79, temp_min = 22.78), name = "Madrid", sys = Sys(country = "ES", sunrise = 1660714052, sunset = 1660763436), visibility = 10000, weather = listOf(Weather(description = "scattered clouds", icon = "03d", id = 802, main = "Clouds")), wind = Wind(deg = 80, speed = 3.6), timezone = 0
            ),
            WeatherObject(
                isCurrentLocation = false, clouds = Clouds(all = 75), coord = Coord(lat = 48.8534, lon = 2.3488), dt = 1660759785, id = 2988507, main = Main(feels_like = 22.52, grnd_level = 0, humidity = 74, pressure = 1011, sea_level = 0, temp = 22.3, temp_max = 26.34, temp_min = 19.88), name = "Paris", sys = Sys(country = "FR", sunrise = 1660711558, sunset = 1660763025), visibility = 5000, weather = listOf(Weather(description = "light rain", icon = "10d", id = 500, main = "Rain")), wind = Wind(deg = 130, speed = 6.17), timezone = 0
            )
        )
    )
}
