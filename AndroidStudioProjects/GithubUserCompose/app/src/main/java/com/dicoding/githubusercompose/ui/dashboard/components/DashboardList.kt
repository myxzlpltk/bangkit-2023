package com.dicoding.githubusercompose.ui.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import com.dicoding.githubusercompose.R
import com.dicoding.githubusercompose.data.entities.User
import com.dicoding.githubusercompose.ui.dashboard.LocalDashboardActions
import com.dicoding.githubusercompose.ui.shared.UserListItem

@Composable
fun DashboardList(
    modifier: Modifier = Modifier,
    pager: LazyPagingItems<User>,
    listState: LazyListState = rememberLazyListState()
) {
    val actions = LocalDashboardActions.current

    LazyColumn(
        modifier = modifier,
        state = if (pager.itemCount > 0) listState else rememberLazyListState()
    ) {
        items(pager, key = { it.id }) { user ->
            user?.let {
                UserListItem(
                    login = user.login,
                    imageUrl = user.avatarUrl,
                    navigateToDetail = actions.navigateToDetail
                )
            }
        }

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
                }
            }
        }
    }
}