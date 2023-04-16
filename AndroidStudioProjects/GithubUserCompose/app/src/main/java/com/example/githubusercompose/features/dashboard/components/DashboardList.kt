package com.example.githubusercompose.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.githubusercompose.components.UserListItem
import com.example.githubusercompose.data.entities.User

@Composable
fun DashboardList(
    modifier: Modifier = Modifier,
    pager: LazyPagingItems<User>,
) {
    LazyColumn(modifier) {
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
                    verticalArrangement = Arrangement.Center,
                ) { Text("Cannot fetch list") }
            }
            else -> items(pager) { user ->
                user?.let {
                    UserListItem(
                        login = user.login,
                        imageUrl = user.avatarUrl
                    )
                }
            }
        }
    }
}