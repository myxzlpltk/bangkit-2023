package com.example.githubusercompose.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.githubusercompose.R
import com.example.githubusercompose.components.UserListItem
import com.example.githubusercompose.data.entities.User

@Composable
fun DashboardList(
    modifier: Modifier = Modifier,
    pager: LazyPagingItems<User>,
    navigateToDetail: (String) -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        when (pager.loadState.refresh) {
            is LoadState.Loading -> item {
                Column(
                    modifier = Modifier.fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) { CircularProgressIndicator() }
            }
            is LoadState.Error -> item {
                Column(
                    modifier = Modifier.fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                ) {
                    Text(stringResource(R.string.cannot_fetch_user_list))
                    Button(onClick = { pager.retry() }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
            else -> {
                if (pager.itemCount == 0) {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            ),
                        ) {
                            Text(stringResource(R.string.no_data_user_list))
                        }
                    }
                } else {
                    items(pager, key = { it.id }) { user ->
                        user?.let {
                            UserListItem(
                                login = user.login,
                                imageUrl = user.avatarUrl,
                                navigateToDetail = navigateToDetail
                            )
                        }
                    }
                }
            }
        }
    }
}