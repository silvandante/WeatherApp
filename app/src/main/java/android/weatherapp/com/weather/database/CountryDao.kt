package android.weatherapp.com.weather.database

import android.weatherapp.com.weather.model.COUNTRY_TABLE
import android.weatherapp.com.weather.model.Country
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: Country)

    @Query("SELECT * FROM $COUNTRY_TABLE")
    suspend fun getCountries(): List<Country>?
}
