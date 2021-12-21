package com.kdg.hilt.mvvm.data.remote

import android.content.Context
import com.kdg.hilt.mvvm.data.remote.domain.framework.Result
import com.kdg.hilt.mvvm.data.remote.domain.framework.safeApiCall
import com.kdg.hilt.mvvm.data.remote.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val RESPONSE_ERROR_USERS = "Error requesting users"
private const val RESPONSE_ERROR_USER_PROFILE = "Error requesting user profile"

class GitHubDataSource @Inject constructor(
    private val gitHubService: GitHubService,
    @ApplicationContext private val context: Context
) {

    suspend fun getUsers(since: Int): Result<List<User>> =
        when (val result =
            safeApiCall({ gitHubService.getUsers(since) }, RESPONSE_ERROR_USERS, context)) {
            is Result.Success -> {
                Result.Success(result.data.map { it.toUser() })
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }

    suspend fun getUserProfile(username: String): Result<User> =
        when (val result = safeApiCall(
            { gitHubService.getUserProfile(username) },
            RESPONSE_ERROR_USER_PROFILE,
            context
        )) {
            is Result.Success -> {
                Result.Success(result.data.toUserProfile())
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }
}
