package com.dicoding.githubusercompose.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class FavoritesCoordinator(
    val viewModel: FavoritesViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun unfavorite(login: String) = viewModel.unfavorite(login)
}

@Composable
fun rememberFavoritesCoordinator(
    viewModel: FavoritesViewModel = hiltViewModel()
): FavoritesCoordinator {
    return remember(viewModel) {
        FavoritesCoordinator(
            viewModel = viewModel
        )
    }
}