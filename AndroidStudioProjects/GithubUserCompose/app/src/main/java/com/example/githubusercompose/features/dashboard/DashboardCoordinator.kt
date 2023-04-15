package com.example.githubusercompose.features.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class DashboardCoordinator(
    val viewModel: DashboardViewModel
) {
    val screenStateFlow = viewModel.stateFlow
    val userPager = viewModel.userPager

    fun doStuff() {
        // TODO Handle UI Action
    }
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