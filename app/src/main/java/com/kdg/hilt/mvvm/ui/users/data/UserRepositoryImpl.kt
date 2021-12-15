package com.kdg.hilt.mvvm.ui.users.data

import com.kdg.hilt.mvvm.data.remote.GitHubDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class UserRepositoryImpl @Inject constructor(
    private val gitHubDataSource: GitHubDataSource
) : UserRepository {

    override suspend fun getUsers(since: Int) = gitHubDataSource.getUsers(since)

    override suspend fun getUserProfile(username: String) =
        gitHubDataSource.getUserProfile(username)
}
