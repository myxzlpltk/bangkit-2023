package com.example.githubusercompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubusercompose.config.INITIAL_PAGE_INDEX
import com.example.githubusercompose.data.entities.User
import com.example.githubusercompose.data.responses.toListUser
import com.example.githubusercompose.data.services.UserService

class SearchUserPagingSource(
    private val query: String,
    private val userService: UserService,
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val loadSize = params.loadSize
            val nextPageNumber = if (params is LoadParams.Refresh) {
                INITIAL_PAGE_INDEX
            } else {
                params.key ?: INITIAL_PAGE_INDEX
            }
            val response = userService.findByLogin(query, nextPageNumber, loadSize)

            LoadResult.Page(
                data = response.toListUser(),
                prevKey = null,
                nextKey = if (response.items.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}