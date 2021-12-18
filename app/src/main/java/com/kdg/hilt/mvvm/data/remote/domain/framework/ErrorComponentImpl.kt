package com.kdg.hilt.mvvm.data.remote.domain.framework

import com.kdg.hilt.mvvm.data.remote.domain.exception.NetworkException
import com.kdg.hilt.mvvm.data.remote.domain.exception.SessionExpiredException
import javax.inject.Inject

class ErrorComponentImpl @Inject constructor() : ErrorComponent {
    override suspend fun processError(
        error: Result.Error,
        networkErrorFunction: () -> Unit,
        genericErrorFunction: () -> Unit,
        sessionExpiredFunction: () -> Unit
    ) {
        when (error.exception) {
            is NetworkException -> {
                networkErrorFunction()
            }
            is SessionExpiredException -> {
                sessionExpiredFunction()
            }
            else -> {
                genericErrorFunction()
            }
        }
    }
}
