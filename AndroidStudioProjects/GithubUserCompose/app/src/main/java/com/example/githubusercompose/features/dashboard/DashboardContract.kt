package com.example.githubusercompose.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents DashboardScreen
 **/
class DashboardState

/**
 * Dashboard Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class DashboardActions(
    val onClick: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalDashboardActions = staticCompositionLocalOf<DashboardActions> {
    error("{NAME} Actions Were not provided, make sure ProvideDashboardActions is called")
}

@Composable
fun ProvideDashboardActions(actions: DashboardActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDashboardActions provides actions) {
        content.invoke()
    }
}

