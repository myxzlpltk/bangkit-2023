package com.dicoding.githubusercompose.ui.favorites

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.dicoding.githubusercompose.data.entities.User


/**
 * UI State that represents FavoritesScreen
 **/
class FavoritesState(
    val loading: Boolean = true,
    val listState: LazyListState = LazyListState(),
    val favoriteUsers: List<User> = emptyList(),
)

fun FavoritesState.copy(
    loading: Boolean? = null,
    listState: LazyListState? = null,
    favoriteUsers: List<User>? = null,
) = FavoritesState(
    loading = loading ?: this.loading,
    listState = listState ?: this.listState,
    favoriteUsers = favoriteUsers ?: this.favoriteUsers,
)

/**
 * Favorites Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class FavoritesActions(
    val navigateBack: () -> Unit = {},
    val navigateToDetail: (String) -> Unit = {},
    val unfavorite: (String) -> Unit = {},
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalFavoritesActions = staticCompositionLocalOf<FavoritesActions> {
    error("{NAME} Actions Were not provided, make sure ProvideFavoritesActions is called")
}

@Composable
fun ProvideFavoritesActions(actions: FavoritesActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalFavoritesActions provides actions) {
        content.invoke()
    }
}

