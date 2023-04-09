package com.dicoding.storyapp.data.source

import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.data.remote.response.CreateStoryResponse
import com.dicoding.storyapp.utils.Configuration.START_PAGE_INDEX
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
        latitude: Double?,
        longitude: Double?,
    ): Flow<ApiResponse<CreateStoryResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                reduceFileImage(file)
                val rDescription = RequestBody.create(MediaType.parse("text/plain"), description)
                val rLatitude = latitude?.let {
                    RequestBody.create(MediaType.parse("text/plain"), latitude.toString())
                }
                val rLongitude = longitude?.let {
                    RequestBody.create(MediaType.parse("text/plain"), longitude.toString())
                }
                val rFile = MultipartBody.Part.createFormData(
                    "photo", file.name, RequestBody.create(MediaType.parse("image/*"), file)
                )
                val response = storyService.create(rFile, rDescription, rLatitude, rLongitude)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                emit(ApiResponse.Error(message))
            }
        }
    }

    fun getAll(withLocation: Boolean): Flow<ApiResponse<List<Story>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)

                val location = if (withLocation) 1 else 0
                val stories = ArrayList<Story>()
                var page = START_PAGE_INDEX

                while (true) {
                    val response = storyService.getAll(page = page, location = location)
                    if (response.listStory.isNotEmpty()) {
                        stories.addAll(response.toListStory())
                        page++
                        emit(ApiResponse.Success(stories))
                    } else {
                        break
                    }
                }
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                emit(ApiResponse.Error(message))
            }
        }
    }
}