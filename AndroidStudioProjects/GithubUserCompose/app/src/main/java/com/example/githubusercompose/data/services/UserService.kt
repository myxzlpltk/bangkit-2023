package com.example.githubusercompose.data.services

import com.example.githubusercompose.config.PAGE_SIZE
import com.example.githubusercompose.data.responses.SearchResponse
import com.example.githubusercompose.data.responses.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("users")
    suspend fun getAll(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = PAGE_SIZE,
    ) : List<UserResponse>

    @GET("search/users")
    suspend fun findByLogin(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_SIZE,
    ): SearchResponse

    @GET("users/{login}")
    suspend fun getByLogin(@Path("login") login: String): UserResponse
}