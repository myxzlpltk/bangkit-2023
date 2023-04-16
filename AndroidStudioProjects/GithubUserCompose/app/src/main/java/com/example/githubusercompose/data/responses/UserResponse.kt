package com.example.githubusercompose.data.responses

import com.example.githubusercompose.data.entities.User
import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

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
)

fun List<UserResponse>.toListUser() = this.map { userResponse -> userResponse.toUser() }
fun UserResponse.toUser() = User(
    this.id,
    this.login.lowercase(),
    this.name,
    this.avatarUrl,
    this.bio,
    this.publicRepos,
    this.following,
    this.followers,
)