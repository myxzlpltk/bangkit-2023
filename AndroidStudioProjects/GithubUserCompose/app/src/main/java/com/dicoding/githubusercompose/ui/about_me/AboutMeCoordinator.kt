package com.dicoding.githubusercompose.ui.about_me

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class AboutMeCoordinator(
    val viewModel: AboutMeViewModel
) {
    val screenStateFlow = viewModel.stateFlow
}

@Composable
fun rememberAboutMeCoordinator(
    viewModel: AboutMeViewModel = hiltViewModel()
): AboutMeCoordinator {
    return remember(viewModel) {
        AboutMeCoordinator(
            viewModel = viewModel
        )
    }
}