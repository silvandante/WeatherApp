package android.weatherapp.com.weather.network

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data = data)
    class Error<T>(errorMessage: String) : NetworkResult<T>(message = errorMessage)
    object Loading : NetworkResult<Nothing>()
}
