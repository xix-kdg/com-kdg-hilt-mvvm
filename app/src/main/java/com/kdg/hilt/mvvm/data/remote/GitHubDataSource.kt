package com.kdg.hilt.mvvm.data.remote

import com.kdg.hilt.mvvm.data.remote.domain.framework.Result
import com.kdg.hilt.mvvm.data.remote.domain.framework.safeApiCall
import com.kdg.hilt.mvvm.data.remote.domain.model.User
import com.kdg.hilt.mvvm.data.remote.response.GetUserProfileResponse
import javax.inject.Inject

class GitHubDataSource @Inject constructor(private val gitHubService: GitHubService) {

    suspend fun getUsers(since: Int): Result<List<User>> {
        return when (val result = safeApiCall { gitHubService.getUsers(since) }) {
            is Result.Success -> {
                Result.Success(result.data.map { it.toUser() })
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }
    }

    // todo: adjust return value to non response object
    suspend fun getUserProfile(username: String): Result<GetUserProfileResponse> {
        return when (val result = safeApiCall { gitHubService.getUserProfile(username) }) {
            is Result.Success -> {
                Result.Success(result.data)
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }
    }
}
