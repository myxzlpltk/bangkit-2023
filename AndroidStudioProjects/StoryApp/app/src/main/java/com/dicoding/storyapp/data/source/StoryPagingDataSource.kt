package com.dicoding.storyapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.remote.StoryService
import com.dicoding.storyapp.utils.Configuration.START_PAGE_INDEX

class StoryPagingDataSource(
    private val token: String,
    private val storyService: StoryService,
) : PagingSource<Int, Story>() {

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: START_PAGE_INDEX
            val response = storyService.getStories("Bearer $token", page, params.loadSize)
            LoadResult.Page(
                data = response.toListStory(),
                prevKey = null,
                nextKey = if (response.listStory.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}