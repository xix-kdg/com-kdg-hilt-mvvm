package com.kdg.hilt.mvvm.data.remote.domain.model

data class User(
    val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val company: String,
    val blog: String,
    val followerCount: String,
    val followingCount: String
)

