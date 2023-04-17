package com.dicoding.githubusercompose.ui.dashboard

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun DashboardRoute(
    coordinator: DashboardCoordinator = rememberDashboardCoordinator(),
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState()
    val pager = coordinator.pager.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    // UI Actions
    val actions = rememberDashboardActions(coordinator)

    // UI Rendering
    DashboardScreen(uiState, actions, pager, listState)
}


@Composable
fun rememberDashboardActions(coordinator: DashboardCoordinator): DashboardActions {
    return remember(coordinator) {
        DashboardActions(
            /* Search View */
            toggleSearch = coordinator::toggleSearch,
            clearSearch = coordinator::clearSearch,
            updateSearch = coordinator::updateSearch,

            /* Overflow Menu */
            toggleOverflowMenu = coordinator::toggleOverflowMenu,
        )
    }
}