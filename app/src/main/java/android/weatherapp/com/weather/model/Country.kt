package android.weatherapp.com.weather.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

const val COUNTRY_TABLE = "countries_table"

@Entity(tableName = COUNTRY_TABLE)
@Parcelize
data class Country(
    var idApi: String? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable {
    override fun toString(): String {
        return idApi ?: ""
    }
}
