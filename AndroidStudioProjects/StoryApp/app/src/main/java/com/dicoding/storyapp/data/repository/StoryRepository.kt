package com.dicoding.storyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.data.remote.response.CreateStoryResponse
import com.dicoding.storyapp.data.source.StoryDataSource
import com.dicoding.storyapp.data.source.StoryPagingDataSource
import com.dicoding.storyapp.utils.Configuration.MAX_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
    private val pref: UserPreference,
    private val storyService: StoryService,
    private val storyDataSource: StoryDataSource,
) {

    fun getAll(): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { StoryPagingDataSource(pref, storyService) },
        ).flow
    }

    suspend fun create(
        file: File,
        description: String,
    ): Flow<ApiResponse<CreateStoryResponse>> {
        return storyDataSource.create(pref.getToken(), file, description)
    }
}