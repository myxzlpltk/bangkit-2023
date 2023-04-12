package com.dicoding.mynavdrawer

import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyNavDrawerState(
    val scaffoldState: ScaffoldState,
    private val scope: CoroutineScope,
    private val context: Context,
) {

  fun onMenuClick() {
    scope.launch {
      // Launch drawer
      scaffoldState.drawerState.open()
    }
  }

  fun onBackPress() {
    if (scaffoldState.drawerState.isOpen) {
      scope.launch {
        // Close drawer
        scaffoldState.drawerState.close()
      }
    }
  }

  fun onItemSelected(title: String) {
    // Show snackbar
    scope.launch {
      // Close drawer
      scaffoldState.drawerState.close()

      // Open snackbar
      val snackbarResult =
          scaffoldState.snackbarHostState.showSnackbar(
              message = context.resources.getString(R.string.coming_soon, title),
              actionLabel = context.resources.getString(R.string.subscribe_question),
          )

      if (snackbarResult == SnackbarResult.ActionPerformed) {
        // Show subscription notification via toast
        Toast.makeText(
                context,
                context.resources.getString(R.string.subscribed_info),
                Toast.LENGTH_SHORT,
            )
            .show()
      }
    }
  }
}

@Composable
fun rememberMyNavDrawerState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
): MyNavDrawerState =
    remember(scaffoldState, coroutineScope, context) {
      MyNavDrawerState(scaffoldState, coroutineScope, context)
    }
