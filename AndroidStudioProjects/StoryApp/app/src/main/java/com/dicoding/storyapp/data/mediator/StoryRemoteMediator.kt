package com.dicoding.storyapp.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dicoding.storyapp.data.entity.RemoteKey
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.local.database.StoryDatabase
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.utils.Configuration.START_PAGE_INDEX
import com.dicoding.storyapp.utils.Configuration.STORY_REMOTE_KEY_ID
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class StoryRemoteMediator @Inject constructor(
    private val database: StoryDatabase,
    private val storyService: StoryService,
) : RemoteMediator<Int, Story>() {

    private val storyDao = database.getStoryDao()
    private val remoteKeyDao = database.getRemoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Story>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> START_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyById(STORY_REMOTE_KEY_ID)
                    }
                    remoteKey.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
            }

            val response = storyService.getAll(page)
            val stories = response.toListStory()
            val endOfPaginationReached = stories.isEmpty()
            val nextKey = if (endOfPaginationReached) null else page + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteById(STORY_REMOTE_KEY_ID)
                    storyDao.deleteAll()
                }

                remoteKeyDao.insertOrReplace(RemoteKey(STORY_REMOTE_KEY_ID, nextKey))
                storyDao.insertAll(stories)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}