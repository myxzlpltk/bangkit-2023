package com.example.githubusercompose.features.about

/**
 * About Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class AboutActions(
    val navigateBack: () -> Unit = {},
    val navigateToGithub: () -> Unit = {},
)