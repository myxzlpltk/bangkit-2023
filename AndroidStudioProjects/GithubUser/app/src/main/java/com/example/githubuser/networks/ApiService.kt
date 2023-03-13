package com.example.githubuser.networks

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun findUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): Call<SearchUsersResponse>

    @GET("users/{username}")
    fun findUser(@Path("username") username: String): Call<UserDetailsResponse>
}