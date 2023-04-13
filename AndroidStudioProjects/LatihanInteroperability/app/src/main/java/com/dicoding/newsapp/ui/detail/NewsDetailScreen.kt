package com.dicoding.newsapp.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.dicoding.newsapp.data.local.entity.NewsEntity

@Composable
fun NewsDetailScreen(
    newsDetail: NewsEntity,
    viewModel: NewsDetailViewModel,
) {
    viewModel.setNewsData(newsDetail)
    val bookmarkStatus by viewModel.bookmarkStatus.observeAsState(false)

    NewsDetailContent(
        newsDetail.title,
        newsDetail.url.toString(),
        bookmarkStatus,
        updateBookmarkStatus = {
            viewModel.changeBookmark(newsDetail)
        }
    )
}