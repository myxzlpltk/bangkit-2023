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
    val pager = coordinator.pager.collectAsLazyPagingItems()

    // UI Actions
    val actions = rememberDashboardActions(coordinator)

    // UI Rendering
    DashboardScreen(uiState, actions, pager)
}


@Composable
fun rememberDashboardActions(coordinator: DashboardCoordinator): DashboardActions {
    return remember(coordinator) {
        DashboardActions(
            openSearch = coordinator::openSearch,
            clearSearch = coordinator::clearSearch,
            closeSearch = coordinator::closeSearch,
            onValueChange = coordinator::onValueChange,
        )
    }
}