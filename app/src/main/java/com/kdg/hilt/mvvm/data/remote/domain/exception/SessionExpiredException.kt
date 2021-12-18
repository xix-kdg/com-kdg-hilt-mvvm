package com.kdg.hilt.mvvm.data.remote.domain.exception

import java.io.IOException

class SessionExpiredException(message: String?) : IOException("Session expired. $message")
