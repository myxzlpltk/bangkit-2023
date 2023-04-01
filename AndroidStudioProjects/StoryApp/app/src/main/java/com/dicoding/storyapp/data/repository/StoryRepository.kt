package com.dicoding.storyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.preference.UserPreference
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.data.source.StoryPagingDataSource
import com.dicoding.storyapp.utils.Configuration.MAX_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
    private val pref: UserPreference,
    private val storyService: StoryService,
) {

    fun getStories(): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_PAGE_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = { StoryPagingDataSource(pref, storyService) },
        ).flow
    }
}