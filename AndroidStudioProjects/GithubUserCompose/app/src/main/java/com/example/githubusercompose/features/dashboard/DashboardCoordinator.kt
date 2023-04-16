package com.example.githubusercompose.features.dashboard

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
    val scrollState = viewModel.scrollState
    val listState = viewModel.listState

    fun openSearch() = viewModel.openSearch()
    fun clearSearch() = viewModel.clearSearch()
    fun closeSearch() = viewModel.closeSearch()
    fun onValueChange(value: TextFieldValue) = viewModel.onValueChange(value)
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