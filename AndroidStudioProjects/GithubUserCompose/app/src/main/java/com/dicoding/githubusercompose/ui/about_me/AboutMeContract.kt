package com.dicoding.githubusercompose.ui.about_me

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.dicoding.githubusercompose.data.entities.User


/**
 * UI State that represents AboutMeScreen
 **/
sealed class AboutMeState {
    object Loading : AboutMeState()
    class Error(val message: String) : AboutMeState()
    class Success(val user: User) : AboutMeState()
}

/**
 * AboutMe Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class AboutMeActions(
    val navigateBack: () -> Unit = {},
    val navigateToGithub: (String) -> Unit = {},
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalAboutMeActions = staticCompositionLocalOf<AboutMeActions> {
    error("{NAME} Actions Were not provided, make sure ProvideAboutMeActions is called")
}

@Composable
fun ProvideAboutMeActions(actions: AboutMeActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAboutMeActions provides actions) {
        content.invoke()
    }
}

