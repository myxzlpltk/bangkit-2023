package com.example.githubusercompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubusercompose.config.INITIAL_ID
import com.example.githubusercompose.data.entities.User
import com.example.githubusercompose.data.responses.toListUser
import com.example.githubusercompose.data.services.UserService

class UserPagingSource(private val userService: UserService) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val loadSize = params.loadSize
            val nextPageNumber = params.key ?: INITIAL_ID
            val response = userService.getAll(nextPageNumber, loadSize)

            LoadResult.Page(
                data = response.toListUser(),
                prevKey = null,
                nextKey = response.lastOrNull()?.id
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}