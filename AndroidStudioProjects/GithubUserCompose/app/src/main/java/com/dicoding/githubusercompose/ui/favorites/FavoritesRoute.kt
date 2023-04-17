package com.dicoding.githubusercompose.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FavoritesRoute(
    coordinator: FavoritesCoordinator = rememberFavoritesCoordinator(),
    navigateBack: () -> Unit = {},
    navigateToDetail: (String) -> Unit = {},
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(FavoritesState())

    // UI Actions
    val actions = rememberFavoritesActions(coordinator, navigateBack, navigateToDetail)

    // UI Rendering
    FavoritesScreen(uiState, actions)
}


@Composable
fun rememberFavoritesActions(
    coordinator: FavoritesCoordinator,
    navigateBack: () -> Unit = {},
    navigateToDetail: (String) -> Unit = {},
): FavoritesActions {
    return remember(coordinator) {
        FavoritesActions(
            navigateBack = navigateBack,
            navigateToDetail = navigateToDetail,
            unfavorite = coordinator::unfavorite,
        )
    }
}