package com.dicoding.storyapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.local.database.StoryDatabase
import com.dicoding.storyapp.data.mediator.StoryRemoteMediator
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.data.remote.response.CreateStoryResponse
import com.dicoding.storyapp.data.source.StoryDataSource
import com.dicoding.storyapp.utils.Configuration.MAX_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
    private val storyService: StoryService,
    private val storyDataSource: StoryDataSource,
    private val database: StoryDatabase,
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPagingData(): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE),
            pagingSourceFactory = { database.storyDao().pagingSource() },
            remoteMediator = StoryRemoteMediator(database, storyService)
        ).flow
    }

    fun getAll(withLocation: Boolean): Flow<ApiResponse<List<Story>>> {
        return storyDataSource.getAll(withLocation)
    }

    suspend fun create(
        file: File,
        description: String,
        latitude: Double?,
        longitude: Double?,
    ): Flow<ApiResponse<CreateStoryResponse>> {
        return storyDataSource.create(file, description, latitude, longitude)
    }

    fun getRandom(): Flow<Story> {
        return database.storyDao().getRandom()
    }
}