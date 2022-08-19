package android.weatherapp.com.weather.network

import android.weatherapp.com.weather.model.WeatherObject
import android.weatherapp.com.weather.model.Weathers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "d239c2d764a51027714f9d644a311967"
    }

    @GET("data/2.5/group")
    suspend fun getWeathersList(
        @Query("id") citiesIds: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<Weathers>

    @GET("data/2.5/weather")
    suspend fun getWeatherByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherObject>

    @GET("data/2.5/weather")
    suspend fun getWeatherByCityName(
        @Query("q") citiesIds: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherObject>
}
