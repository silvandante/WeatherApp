package android.weatherapp.com.weather.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> request(event: suspend () -> Response<T>): Flow<NetworkResult<out T?>> {
    return flow {
        try {
            emit(NetworkResult.Loading)
            val response: Response<T> = event()

            if (response.isSuccessful) {
                emit(NetworkResult.Success(data = response.body()!!))
            } else {
                emit(NetworkResult.Error(errorMessage = response.message() ?: "Something went wrong"))
            }
        } catch (e: HttpException) {
            emit(NetworkResult.Error(errorMessage = e.message ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(NetworkResult.Error("Please check your network connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(errorMessage = e.message ?: "Something went wrong"))
        }
    }
}
