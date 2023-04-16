package com.example.githubusercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import com.example.githubusercompose.ui.theme.AppTheme
import com.example.githubusercompose.ui.theme.md_theme_dark_primaryContainer
import com.example.githubusercompose.ui.theme.md_theme_light_primaryContainer
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContent {
            AppTheme {
                // Remember a SystemUiController
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                DisposableEffect(systemUiController, useDarkIcons) {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    if (useDarkIcons) {
                        systemUiController.setSystemBarsColor(
                            color = md_theme_light_primaryContainer,
                            darkIcons = true
                        )
                    } else {
                        systemUiController.setSystemBarsColor(
                            color = md_theme_dark_primaryContainer,
                            darkIcons = false
                        )
                    }

                    onDispose {}
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Router()
                }
            }
        }
    }
}