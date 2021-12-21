package com.kdg.hilt.mvvm.data.remote.response

import com.kdg.hilt.mvvm.data.remote.domain.model.User

class GetUsersResponse : ArrayList<GetUsersResponse.GetUsersResponseItem>() {
    data class GetUsersResponseItem(
        val login: String = "",
        val id: Int = 0,
        val node_id: String = "",
        val avatar_url: String = "",
        val gravatar_id: String = "",
        val url: String = "",
        val html_url: String = "",
        val followers_url: String = "",
        val following_url: String = "",
        val gists_url: String = "",
        val starred_url: String = "",
        val subscriptions_url: String = "",
        val organizations_url: String = "",
        val repos_url: String = "",
        val events_url: String = "",
        val received_events_url: String = "",
        val type: String = "",
        val site_admin: Boolean = false,
    ) {
        fun toUser(): User {
            return User(
                id = id,
                login = login,
                name = "",
                avatarUrl = avatar_url,
                company = "",
                blog = "",
                followerCount = "0",
                followingCount = "0"
            )
        }
    }
}
