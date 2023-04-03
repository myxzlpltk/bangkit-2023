package com.dicoding.storyapp.data.remote

import com.dicoding.storyapp.data.remote.response.CreateStoryResponse
import com.dicoding.storyapp.data.remote.response.StoriesResponse
import com.dicoding.storyapp.utils.Configuration.MAX_PAGE_SIZE
import com.dicoding.storyapp.utils.Configuration.START_PAGE_INDEX
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryService {

    @GET("stories")
    suspend fun getAll(
        @Query("page") page: Int = START_PAGE_INDEX,
        @Query("size") size: Int = MAX_PAGE_SIZE,
        @Query("location") location: Int = 0,
    ): StoriesResponse

    @Multipart
    @POST("stories")
    suspend fun create(
        @Part filePart: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): CreateStoryResponse
}