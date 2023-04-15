package com.example.githubusercompose.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun DashboardRoute(
    coordinator: DashboardCoordinator = rememberDashboardCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(DashboardState())
    val userPager = coordinator.userPager.collectAsLazyPagingItems()

    // UI Actions
    val actions = rememberDashboardActions(coordinator)

    // UI Rendering
    DashboardScreen(uiState, actions, userPager)
}


@Composable
fun rememberDashboardActions(coordinator: DashboardCoordinator): DashboardActions {
    return remember(coordinator) {
        DashboardActions(
            onClick = coordinator::doStuff
        )
    }
}