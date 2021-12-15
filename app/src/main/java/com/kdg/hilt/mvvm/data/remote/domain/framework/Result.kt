package com.kdg.hilt.mvvm.data.remote.domain.framework

import retrofit2.Response

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                return Result.Success(body)
            }
        }
        return Result.Error(Exception("${response.code()} ${response.message()}"))
    } catch (e: Exception) {
        return Result.Error(Exception(e.message ?: e.toString()))
    }
}
