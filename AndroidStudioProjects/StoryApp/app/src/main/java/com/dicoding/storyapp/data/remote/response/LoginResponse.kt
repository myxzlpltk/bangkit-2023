package com.dicoding.storyapp.data.remote.response

import com.dicoding.storyapp.data.entity.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("loginResult") val loginResult: LoginResult,

    @field:SerializedName("error") val error: Boolean,

    @field:SerializedName("message") val message: String,
) {
    fun toUser() = User(
        loginResult.userId,
        loginResult.name,
        loginResult.token,
    )
}

data class LoginResult(

    @field:SerializedName("name") val name: String,

    @field:SerializedName("userId") val userId: String,

    @field:SerializedName("token") val token: String,
)