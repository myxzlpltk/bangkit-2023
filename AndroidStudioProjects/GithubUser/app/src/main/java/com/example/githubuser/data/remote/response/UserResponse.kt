package com.example.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    private val _username: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("bio")
    val bio: String? = null,

    @field:SerializedName("public_repos")
    val publicRepos: Int = 0,

    @field:SerializedName("following")
    val following: Int = 0,

    @field:SerializedName("followers")
    val followers: Int = 0,
) {
    val username get() = _username.lowercase()
}