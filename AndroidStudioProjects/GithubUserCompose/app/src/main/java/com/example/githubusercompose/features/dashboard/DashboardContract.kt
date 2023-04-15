package com.example.githubusercompose.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.githubusercompose.data.entities.User


/**
 * UI State that represents DashboardScreen
 **/
class DashboardState(
    val users: List<User> = emptyList(),
) {
    fun copy(users: List<User>? = null): DashboardState {
        return DashboardState(
            users = users ?: this.users
        )
    }
}

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

