package com.dicoding.newsapp.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.newsapp.R

@Composable
fun NewsDetailContent(
    title: String,
    url: String,
    bookmarkStatus: Boolean,
    updateBookmarkStatus: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(onClick = updateBookmarkStatus) {
                        Icon(
                            painter = if (bookmarkStatus) {
                                painterResource(R.drawable.ic_bookmarked_white)
                            } else {
                                painterResource(R.drawable.ic_bookmark_white)
                            },
                            contentDescription = stringResource(R.string.save_bookmark),
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsDetailContentPreview() {
    MaterialTheme {
        NewsDetailContent("New News", "www.dicoding.com", false, {})
    }
}