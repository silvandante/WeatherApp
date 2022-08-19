package android.weatherapp.com.weather.location

class LocationManagerService {
    private var latitude: String? = null
    private var longitude: String? = null

    fun setLatitudeLocation(lat: String, lon: String) {
        latitude = lat
        longitude = lon
    }

    fun getLocationManagerService(): Boolean {
        return latitude?.isNotEmpty() == true && longitude?.isNotEmpty() == true
    }

    fun getLatitude(): String {
        return latitude ?: ""
    }

    fun getLongitude(): String {
        return longitude ?: ""
    }
}
