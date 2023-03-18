package com.example.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class SimpleUser(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)