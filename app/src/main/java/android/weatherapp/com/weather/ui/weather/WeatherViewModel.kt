package android.weatherapp.com.weather.ui.weather

import android.weatherapp.com.weather.model.WeatherObject
import android.weatherapp.com.weather.model.Weathers
import android.weatherapp.com.weather.network.NetworkResult
import android.weatherapp.com.weather.repositories.CountryRepository
import android.weatherapp.com.weather.repositories.WeatherRepository
import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val countryRepository: CountryRepository
) : ViewModel() {

    private var singleWeather_ = MutableLiveData<NetworkResult<out WeatherObject?>>(null)
    var singleWeather: LiveData<NetworkResult<out WeatherObject?>> = singleWeather_

    private var weathersList_ = MutableLiveData<NetworkResult<out Weathers?>>(null)
    var weathersList: LiveData<NetworkResult<out Weathers?>> = weathersList_

    suspend fun getCountries() = countryRepository.getCountries()

    suspend fun getWeathers(citiesIds: String) {
        weatherRepository.getWeathersList(citiesIds).collect { result ->
            weathersList_.postValue(result)
        }
    }

    suspend fun getWeatherByLatLon(lat: String, lon: String) {
        weatherRepository.getWeatherByLatLon(lat, lon).collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    result.data?.isCurrentLocation = true
                    singleWeather_.postValue(result)
                }
                else -> {
                    singleWeather_.postValue(result)
                }
            }
        }
    }

    suspend fun getWeatherByCityName(cityName: String) {
        weatherRepository.getWeatherByCityName(cityName).collect { result ->
            singleWeather_.postValue(result)
        }
    }
}
