package com.example.githubusercompose.features.detail_user

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailUserScreen(
    state: DetailUserState = DetailUserState(),
    actions: DetailUserActions = DetailUserActions()
) {
    // TODO UI Logic
}

@Composable
@Preview(name = "DetailUser")
private fun DetailUserScreenPreview() {
    DetailUserScreen()
}

