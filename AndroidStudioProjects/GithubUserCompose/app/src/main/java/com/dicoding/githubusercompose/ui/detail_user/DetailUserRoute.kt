package com.dicoding.githubusercompose.ui.detail_user

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.githubusercompose.di.ViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors

@Composable
fun DetailUserRoute(
    login: String = "",
    navigateBack: () -> Unit = {},
) {
    // Use factory instead
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).detailUserViewModelFactory()
    val viewModel: DetailUserViewModel = viewModel(
        factory = DetailUserViewModel.provideDetailUserViewModel(factory, login)
    )
    val coordinator: DetailUserCoordinator = rememberDetailUserCoordinator(viewModel)

    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(DetailUserState.Loading)

    // UI Actions
    val actions = rememberDetailUserActions(coordinator, navigateBack)

    // UI Rendering
    DetailUserScreen(uiState, actions)
}


@Composable
fun rememberDetailUserActions(
    coordinator: DetailUserCoordinator,
    navigateBack: () -> Unit = {},
): DetailUserActions {
    return remember(coordinator) {
        DetailUserActions(
            toggleFavorite = coordinator::toggleFavorite,
            navigateBack = navigateBack,
        )
    }
}