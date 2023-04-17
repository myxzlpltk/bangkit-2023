package com.dicoding.githubusercompose.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.input.TextFieldValue


/**
 * UI State that represents DashboardScreen
 **/
class DashboardState(
    val query: TextFieldValue = TextFieldValue(""),
    val searchVisible: Boolean = false,
    val overflowMenuVisible: Boolean = false,
)

fun DashboardState.copy(
    query: TextFieldValue? = null,
    searchVisible: Boolean? = null,
    overflowMenuVisible: Boolean? = null,
) = DashboardState(
    query = query ?: this.query,
    searchVisible = searchVisible ?: this.searchVisible,
    overflowMenuVisible = overflowMenuVisible ?: this.overflowMenuVisible,
)

/**
 * Dashboard Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class DashboardActions(
    // Navigation
    val navigateToDetail: (String) -> Unit = {},
    val navigateToFavorites: () -> Unit = {},
    val navigateToAbout: () -> Unit = {},

    // SearchView
    val toggleSearch: () -> Unit = {},
    val clearSearch: () -> Unit = {},
    val updateSearch: (TextFieldValue) -> Unit = {},

    // OverflowMenu
    val toggleOverflowMenu: () -> Unit = {},
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
