package com.dicoding.storyapp.data.source

import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.data.remote.response.CreateStoryResponse
import com.dicoding.storyapp.utils.getErrorMessage
import com.dicoding.storyapp.utils.reduceFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryDataSource @Inject constructor(private val storyService: StoryService) {

    suspend fun create(
        file: File,
        description: String,
    ): Flow<ApiResponse<CreateStoryResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                reduceFileImage(file)
                val rDescription = RequestBody.create(MediaType.parse("text/plain"), description)
                val rFile = MultipartBody.Part.createFormData(
                    "photo", file.name, RequestBody.create(MediaType.parse("image/*"), file)
                )
                val response = storyService.create(rFile, rDescription)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                emit(ApiResponse.Error(message))
            }
        }
    }
}