package com.dicoding.githubusercompose.ui.about_me

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AboutMeRoute(
    coordinator: AboutMeCoordinator = rememberAboutMeCoordinator(),
    navigateBack: () -> Unit = {},
    navigateToDetail: (String) -> Unit = {},
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(AboutMeState.Loading)

    // UI Actions
    val actions = rememberAboutMeActions(coordinator, navigateBack, navigateToDetail)

    // UI Rendering
    AboutMeScreen(uiState, actions)
}


@Composable
fun rememberAboutMeActions(
    coordinator: AboutMeCoordinator,
    navigateBack: () -> Unit,
    navigateToDetail: (String) -> Unit,
): AboutMeActions {
    return remember(coordinator) {
        AboutMeActions(
            navigateBack = navigateBack,
            navigateToDetail = navigateToDetail,
        )
    }
}