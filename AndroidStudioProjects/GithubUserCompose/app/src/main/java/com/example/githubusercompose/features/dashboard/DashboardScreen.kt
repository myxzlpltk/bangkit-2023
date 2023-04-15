package com.example.githubusercompose.features.dashboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubusercompose.components.UserListItem

@Composable
fun DashboardScreen(
    state: DashboardState = DashboardState(),
    actions: DashboardActions = DashboardActions()
) {
    LazyColumn {
        items(state.users) { user ->
            UserListItem(
                login = user.login,
                imageUrl = user.avatarUrl
            )
        }
    }
}

@Composable
@Preview(name = "Dashboard")
private fun DashboardScreenPreview() {
    DashboardScreen()
}

