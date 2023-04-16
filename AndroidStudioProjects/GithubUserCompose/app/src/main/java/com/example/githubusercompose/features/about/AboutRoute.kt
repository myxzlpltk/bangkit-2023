package com.example.githubusercompose.features.about

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun AboutRoute(
    navigateBack: () -> Unit,
    navigateToGithub: () -> Unit
) {
    // UI Actions
    val actions = rememberAboutActions(navigateBack, navigateToGithub)

    // UI Rendering
    AboutScreen(actions)
}


@Composable
fun rememberAboutActions(
    navigateBack: () -> Unit,
    navigateToGithub: () -> Unit,
): AboutActions {
    return remember(true) {
        AboutActions(
            navigateBack = navigateBack,
            navigateToGithub = navigateToGithub,
        )
    }
}