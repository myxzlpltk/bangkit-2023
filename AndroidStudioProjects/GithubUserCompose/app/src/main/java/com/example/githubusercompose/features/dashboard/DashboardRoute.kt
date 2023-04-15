package com.example.githubusercompose.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardRoute(
    coordinator: DashboardCoordinator = rememberDashboardCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(DashboardState())

    // UI Actions
    val actions = rememberDashboardActions(coordinator)

    // UI Rendering
    DashboardScreen(uiState, actions)
}


@Composable
fun rememberDashboardActions(coordinator: DashboardCoordinator): DashboardActions {
    return remember(coordinator) {
        DashboardActions(
            onClick = coordinator::doStuff
        )
    }
}