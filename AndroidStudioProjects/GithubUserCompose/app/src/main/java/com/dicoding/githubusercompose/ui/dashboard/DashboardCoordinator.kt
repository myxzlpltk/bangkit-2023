package com.dicoding.githubusercompose.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class DashboardCoordinator(
    val viewModel: DashboardViewModel
) {
    val screenStateFlow = viewModel.stateFlow
    val pager = viewModel.pager
    val listState = viewModel.listState

    /* Search View */
    fun toggleSearch() = viewModel.toggleSearch()
    fun clearSearch() = viewModel.clearSearch()
    fun updateSearch(value: TextFieldValue) = viewModel.updateSearch(value)

    /* Overflow Menu */
    fun toggleOverflowMenu() = viewModel.toggleOverflowMenu()
}

@Composable
fun rememberDashboardCoordinator(
    viewModel: DashboardViewModel = hiltViewModel()
): DashboardCoordinator {
    return remember(viewModel) {
        DashboardCoordinator(
            viewModel = viewModel
        )
    }
}