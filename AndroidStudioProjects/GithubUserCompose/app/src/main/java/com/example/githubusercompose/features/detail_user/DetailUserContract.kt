package com.example.githubusercompose.features.detail_user

import com.example.githubusercompose.data.entities.User


/**
 * UI State that represents DetailUserScreen
 **/
sealed class DetailUserState {
    object Loading : DetailUserState()
    object Error : DetailUserState()
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