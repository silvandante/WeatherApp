package android.weatherapp.com.weather.database

import android.weatherapp.com.weather.model.Country
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Country::class], version = 1)
abstract class CountryAppDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountryDao

    companion object {
        val DATABASE_NAME = "weather_database"
    }
}
