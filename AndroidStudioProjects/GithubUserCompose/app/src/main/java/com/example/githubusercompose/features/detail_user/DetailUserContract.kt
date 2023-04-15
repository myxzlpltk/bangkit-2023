package com.example.githubusercompose.features.detail_user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents DetailUserScreen
 **/
class DetailUserState

/**
 * DetailUser Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class DetailUserActions(
    val onClick: () -> Unit = {}
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

