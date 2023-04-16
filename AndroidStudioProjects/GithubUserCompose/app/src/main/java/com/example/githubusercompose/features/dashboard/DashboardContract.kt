package com.example.githubusercompose.features.dashboard

import androidx.compose.ui.text.input.TextFieldValue


/**
 * UI State that represents DashboardScreen
 **/
class DashboardState(
    val query: TextFieldValue = TextFieldValue(""),
    val search: Boolean = false,
    val overflowMenu: Boolean = false,
)

fun DashboardState.copy(
    query: TextFieldValue? = null,
    search: Boolean? = null,
    overflowMenu: Boolean? = null,
) = DashboardState(
    query = query ?: this.query,
    search = search ?: this.search,
    overflowMenu = overflowMenu ?: this.overflowMenu,
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
    val navigateToDetail: (String) -> Unit = {},
    val openOverflowMenu: () -> Unit = {},
    val closeOverflowMenu: () -> Unit = {},
)
