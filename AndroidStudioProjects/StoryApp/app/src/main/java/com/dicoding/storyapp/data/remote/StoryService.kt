package com.dicoding.storyapp.data.remote

import com.dicoding.storyapp.data.remote.response.StoriesResponse
import com.dicoding.storyapp.utils.Configuration.MAX_PAGE_SIZE
import com.dicoding.storyapp.utils.Configuration.START_PAGE_INDEX
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StoryService {

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int = START_PAGE_INDEX,
        @Query("size") size: Int = MAX_PAGE_SIZE,
        @Query("location") location: Int = 0,
    ): StoriesResponse
}