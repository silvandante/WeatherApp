package android.weatherapp.com.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weathers(
    val cnt: Int,
    val list: List<WeatherObject>
) : Parcelable
