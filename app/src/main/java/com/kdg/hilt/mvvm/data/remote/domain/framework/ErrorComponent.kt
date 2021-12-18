package com.kdg.hilt.mvvm.data.remote.domain.framework

interface ErrorComponent {

    suspend fun processError(
        error: Result.Error,
        networkErrorFunction: () -> Unit,
        genericErrorFunction: () -> Unit,
        sessionExpiredFunction: () -> Unit,
    )
}
