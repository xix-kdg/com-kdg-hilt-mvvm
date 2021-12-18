package com.kdg.hilt.mvvm.data.remote.domain.exception

import java.io.IOException

class NetworkException(message: String?, cause: Throwable) :
    IOException("Network connection error. $message", cause)
