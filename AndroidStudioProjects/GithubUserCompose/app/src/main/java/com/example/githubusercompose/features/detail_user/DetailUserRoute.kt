package com.example.githubusercompose.features.detail_user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailUserRoute(
    coordinator: DetailUserCoordinator = rememberDetailUserCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(DetailUserState())

    // UI Actions
    val actions = rememberDetailUserActions(coordinator)

    // UI Rendering
    DetailUserScreen(uiState, actions)
}


@Composable
fun rememberDetailUserActions(coordinator: DetailUserCoordinator): DetailUserActions {
    return remember(coordinator) {
        DetailUserActions(
            onClick = coordinator::doStuff
        )
    }
}