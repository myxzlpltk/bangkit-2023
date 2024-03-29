package com.dicoding.githubusercompose.ui.detail_user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.dicoding.githubusercompose.data.entities.User


/**
 * UI State that represents DetailUserScreen
 **/
sealed class DetailUserState {
    object Loading : DetailUserState()
    class Error(val message: String) : DetailUserState()
    class Success(val user: User) : DetailUserState()
}

/**
 * DetailUser Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class DetailUserActions(
    val navigateBack: () -> Unit = {},
    val toggleFavorite: () -> Unit = {},
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalDetailUserActions = staticCompositionLocalOf<DetailUserActions> {
    error("{NAME} Actions Were not provided, make sure ProvideDetailUserActions is called")
}

@Composable
fun ProvideDetailUserActions(actions: DetailUserActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDetailUserActions provides actions) {
        content.invoke()
    }
}

