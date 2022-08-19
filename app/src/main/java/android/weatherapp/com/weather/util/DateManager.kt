package android.weatherapp.com.weather.util

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Date

object DateManager {

    fun getDayOfWeek(date: Date): String {
        return when (date.day + 1) {
            Calendar.MONDAY -> "MON"
            Calendar.TUESDAY -> "TUE"
            Calendar.WEDNESDAY -> "WED"
            Calendar.THURSDAY -> "THU"
            Calendar.FRIDAY -> "FRI"
            Calendar.SATURDAY -> "SAT"
            else -> "SUN"
        }
    }

    fun getCurrentDay(date: Date): String {
        return DateFormat.format("dd", date).toString()
    }

    fun getTime(date: Date): String {
        return DateFormat.format("HH:mm", date).toString()
    }
}
