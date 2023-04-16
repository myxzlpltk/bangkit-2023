package com.example.githubusercompose.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.input.TextFieldValue


/**
 * UI State that represents DashboardScreen
 **/
class DashboardState(
    val query: TextFieldValue = TextFieldValue(""),
    val search: Boolean = false,
)

fun DashboardState.copy(
    query: TextFieldValue? = null,
    search: Boolean? = null,
) = DashboardState(
    query = query ?: this.query,
    search = search ?: this.search,
)

/**
 * Dashboard Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class DashboardActions(
    val openSearch: () -> Unit = {},
    val clearSearch: () -> Unit = {},
    val closeSearch: () -> Unit = {},
    val onValueChange: (TextFieldValue) -> Unit = {},
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

