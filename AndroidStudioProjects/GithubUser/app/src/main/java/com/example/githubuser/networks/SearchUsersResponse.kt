package com.example.githubuser.networks

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val items: List<UserResponse>,
)

data class UserResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)
