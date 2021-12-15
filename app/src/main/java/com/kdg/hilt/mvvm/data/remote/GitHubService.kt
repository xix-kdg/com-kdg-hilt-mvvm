package com.kdg.hilt.mvvm.data.remote

import com.kdg.hilt.mvvm.data.remote.response.GetUserProfileResponse
import com.kdg.hilt.mvvm.data.remote.response.GetUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val QUERY_SINCE = "since"
private const val PATH_USERNAME = "username"

interface GitHubService {

    @GET("users")
    suspend fun getUsers(@Query(QUERY_SINCE) since: Int): Response<GetUsersResponse>

    @GET("users/{$PATH_USERNAME}")
    suspend fun getUserProfile(@Path(PATH_USERNAME) username: String): Response<GetUserProfileResponse>
}
