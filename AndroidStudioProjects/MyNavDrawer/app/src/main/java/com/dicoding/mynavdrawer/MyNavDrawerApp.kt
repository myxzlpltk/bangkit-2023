package com.dicoding.mynavdrawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun MyNavDrawerApp() {
  val appState = rememberMyNavDrawerState()

  Scaffold(
      scaffoldState = appState.scaffoldState,
      topBar = {
        MyTopBar(
            onMenuClick = appState::onMenuClick,
        )
      },
      drawerContent = {
        // Drawer content
        MyDrawerContent(
            onItemSelected = appState::onItemSelected,
            onBackPress = appState::onBackPress,
        )
        Text(stringResource(R.string.hello_from_nav_drawer))
      },
  ) { innerPadding ->
    Box(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        contentAlignment = Alignment.Center,
    ) {
      Text(stringResource(R.string.hello_world))
    }
  }
}
