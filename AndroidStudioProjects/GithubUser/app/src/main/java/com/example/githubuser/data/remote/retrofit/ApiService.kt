package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.SearchResponse
import com.example.githubuser.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun findUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun findUser(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/followers")
    fun findFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun findFollowing(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30,
    ): Call<List<UserResponse>>
}