package com.dicoding.githubusercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.githubusercompose.ui.about_me.AboutMeRoute
import com.dicoding.githubusercompose.ui.dashboard.DashboardRoute
import com.dicoding.githubusercompose.ui.detail_user.DetailUserRoute
import com.dicoding.githubusercompose.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContent {
            AppTheme {
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

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Favorites : Screen("favorites")
    object AboutMe : Screen("about-me")
    object DetailUser : Screen("users/{login}") {
        fun createRoute(login: String) = "users/$login"
    }
}

@Composable
fun Router(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardRoute(
                navigateToDetail = { login ->
                    navController.navigate(Screen.DetailUser.createRoute(login))
                },
                navigateToAboutMe = {
                    navController.navigate(Screen.AboutMe.route)
                }
            )
        }
        composable(
            route = Screen.DetailUser.route,
            arguments = listOf(navArgument("login") { type = NavType.StringType })
        ) {
            val login = it.arguments?.getString("login") ?: ""
            DetailUserRoute(
                login = login,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(Screen.AboutMe.route) {
            AboutMeRoute(
                navigateBack = { navController.navigateUp() },
                navigateToDetail = { login ->
                    navController.navigate(Screen.DetailUser.createRoute(login))
                }
            )
        }
        composable(Screen.Favorites.route) {
        }
    }
}