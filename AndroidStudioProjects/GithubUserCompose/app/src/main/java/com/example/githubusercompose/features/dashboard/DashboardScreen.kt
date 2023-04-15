package com.example.githubusercompose.features.dashboard

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
fun DashboardScreen(
    state: DashboardState = DashboardState(),
    actions: DashboardActions = DashboardActions(),
    userPager: LazyPagingItems<User>
) {
    LazyColumn {
        items(userPager) { user ->
            user?.let {
                UserListItem(
                    login = user.login,
                    imageUrl = user.avatarUrl
                )
            }
        }

        when (userPager.loadState.refresh) {
            is LoadState.Loading -> item {
                Column(
                    modifier = Modifier.fillParentMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) { CircularProgressIndicator() }
            }
            is LoadState.Error -> item {
                Text("error")
            }
            else -> {}
        }
    }
}

