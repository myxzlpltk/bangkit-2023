package com.dicoding.mynavdrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.mynavdrawer.ui.theme.MyNavDrawerTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyNavDrawerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
          MyNavDrawerApp()
        }
      }
    }
  }
}

@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
  TopAppBar(
      navigationIcon = {
        IconButton(onClick = { onMenuClick() }) {
          Icon(
              imageVector = Icons.Default.Menu,
              contentDescription = stringResource(R.string.menu),
          )
        }
      },
      title = { Text(stringResource(R.string.app_name)) },
  )
}

data class MenuItem(val title: String, val icon: ImageVector)

@Composable
fun MyDrawerContent(
    modifier: Modifier = Modifier,
    onItemSelected: (title: String) -> Unit,
    onBackPress: () -> Unit,
) {
  val items =
      listOf(
          MenuItem(title = stringResource(R.string.home), icon = Icons.Default.Home),
          MenuItem(title = stringResource(R.string.favourite), icon = Icons.Default.Favorite),
          MenuItem(title = stringResource(R.string.profile), icon = Icons.Default.AccountCircle),
      )

  Column(modifier.fillMaxSize()) {
    Box(
        modifier = Modifier.height(190.dp).fillMaxWidth().background(MaterialTheme.colors.primary),
    )

    for (item in items) {
      Row(
          modifier =
              Modifier.clickable { onItemSelected(item.title) }
                  .padding(vertical = 12.dp, horizontal = 16.dp)
                  .fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = Color.DarkGray,
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.subtitle2,
        )
      }
    }

    Divider()
  }
  BackPressHandler { onBackPress() }
}

@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPressed: () -> Unit) {
  val currentOnBackPressed by rememberUpdatedState(onBackPressed)
  val backCallback = remember {
    object : OnBackPressedCallback(enabled) {
      override fun handleOnBackPressed() {
        currentOnBackPressed()
      }
    }
  }
  SideEffect { backCallback.isEnabled = enabled }
  val backDispatcher =
      checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
            "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
          }
          .onBackPressedDispatcher
  val lifecycleOwner = LocalLifecycleOwner.current
  DisposableEffect(lifecycleOwner, backDispatcher) {
    backDispatcher.addCallback(lifecycleOwner, backCallback)
    onDispose { backCallback.remove() }
  }
}

@Preview(showBackground = true)
@Composable
fun MyNavDrawerAppPreview() {
  MyNavDrawerTheme {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
      MyNavDrawerApp()
    }
  }
}
