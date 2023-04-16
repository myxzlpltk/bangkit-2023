package com.example.githubusercompose.features.detail_user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailUserRoute(
    login: String,
    navigateBack: () -> Unit,
    coordinator: DetailUserCoordinator = rememberDetailUserCoordinator()
) {
    LaunchedEffect(true) {
        coordinator.setUser(login)
    }

    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(DetailUserState.Loading)

    // UI Actions
    val actions = rememberDetailUserActions(coordinator, navigateBack)

    // UI Rendering
    DetailUserScreen(uiState, actions)
}


@Composable
fun rememberDetailUserActions(
    coordinator: DetailUserCoordinator,
    navigateBack: () -> Unit
): DetailUserActions {
    return remember(coordinator) {
        DetailUserActions(
            navigateBack = navigateBack,
        )
    }
}