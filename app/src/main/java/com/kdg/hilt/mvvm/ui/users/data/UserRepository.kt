package com.kdg.hilt.mvvm.ui.users.data

import com.kdg.hilt.mvvm.data.remote.domain.framework.Result
import com.kdg.hilt.mvvm.data.remote.domain.model.User
import com.kdg.hilt.mvvm.data.remote.response.GetUserProfileResponse

interface UserRepository {

    suspend fun getUsers(since: Int): Result<List<User>>

    suspend fun getUserProfile(username: String): Result<GetUserProfileResponse>
}
