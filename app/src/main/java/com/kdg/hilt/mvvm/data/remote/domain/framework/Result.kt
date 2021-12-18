package com.kdg.hilt.mvvm.data.remote.domain.framework

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.kdg.hilt.mvvm.data.remote.domain.exception.NetworkException
import retrofit2.Response
import java.io.IOException

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>,
    errorMessage: String,
    context: Context
): Result<T> {
    return if (!hasInternet(context)) {
        Result.Error(NetworkException(errorMessage, IOException()))
    } else try {
        val response = apiCall()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            // From this block error should be coming from business error (statusCode, message)
            Result.Error(Exception("Error Business Error"))
        }
    } catch (e: Exception) {
        if (!hasInternet(context))
            Result.Error(NetworkException(errorMessage, e))
        else {
            Result.Error(e)
        }
    }
}

private fun hasInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
}
