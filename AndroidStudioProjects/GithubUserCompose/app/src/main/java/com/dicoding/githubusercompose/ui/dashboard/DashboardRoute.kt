package com.dicoding.githubusercompose.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun DashboardRoute(
    coordinator: DashboardCoordinator = rememberDashboardCoordinator(),
    navigateToDetail: (String) -> Unit = {},
    navigateToAboutMe: () -> Unit = {},
    navigateToFavorites: () -> Unit = {},
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState()
    val pager = coordinator.pager.collectAsLazyPagingItems()
    val listState = coordinator.listState

    // UI Actions
    val actions = rememberDashboardActions(
        coordinator,
        navigateToDetail,
        navigateToAboutMe,
        navigateToFavorites,
    )

    // UI Rendering
    DashboardScreen(uiState, actions, pager, listState)
}


@Composable
fun rememberDashboardActions(
    coordinator: DashboardCoordinator,
    navigateToDetail: (String) -> Unit,
    navigateToAboutMe: () -> Unit = {},
    navigateToFavorites: () -> Unit = {},
): DashboardActions {
    return remember(coordinator) {
        DashboardActions(
            /* Navigation */
            navigateToDetail = navigateToDetail,
            navigateToFavorites = navigateToFavorites,
            navigateToAboutMe = navigateToAboutMe,

            /* Search View */
            toggleSearch = coordinator::toggleSearch,
            clearSearch = coordinator::clearSearch,
            updateSearch = coordinator::updateSearch,

            /* Overflow Menu */
            toggleOverflowMenu = coordinator::toggleOverflowMenu,
        )
    }
}