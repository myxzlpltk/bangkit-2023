package com.dicoding.githubusercompose.data.responses

import com.dicoding.githubusercompose.data.entities.User
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
    id = this.id,
    login = this.login.lowercase(),
    name = this.name,
    avatarUrl = this.avatarUrl,
    bio = this.bio,
    publicRepos = this.publicRepos,
    following = this.following,
    followers = this.followers,
)