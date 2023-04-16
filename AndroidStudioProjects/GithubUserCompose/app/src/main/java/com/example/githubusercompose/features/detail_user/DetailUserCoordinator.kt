package com.example.githubusercompose.features.detail_user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class DetailUserCoordinator(
    val viewModel: DetailUserViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun setUser(login: String) {
        viewModel.setUser(login)
    }
}

@Composable
fun rememberDetailUserCoordinator(
    viewModel: DetailUserViewModel = hiltViewModel()
): DetailUserCoordinator {
    return remember(viewModel) {
        DetailUserCoordinator(
            viewModel = viewModel
        )
    }
}