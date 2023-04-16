package com.example.githubusercompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubusercompose.features.about.AboutRoute
import com.example.githubusercompose.features.dashboard.DashboardRoute
import com.example.githubusercompose.features.detail_user.DetailUserRoute
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object DetailUser : Screen("users/{login}") {
        fun createRoute(login: String) = "users/$login"
    }

    object Favorites : Screen("favorites")
    object About : Screen("about")
}

@Composable
fun Router(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    LaunchedEffect(true) {
        delay(1000)
        navController.navigate(Screen.About.route)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardRoute(
                navigateToDetail = { login ->
                    navController.navigate(Screen.DetailUser.createRoute(login))
                },
                navigateToFavorites = {
                    navController.navigate(Screen.Favorites.route)
                },
                navigateToAbout = {
                    navController.navigate(Screen.About.route)
                }
            )
        }
        composable(
            route = Screen.DetailUser.route,
            arguments = listOf(navArgument("login") {
                type = NavType.StringType
            })
        ) {
            val login = it.arguments?.getString("login") ?: ""
            DetailUserRoute(
                login = login,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.Favorites.route) {

        }
        composable(Screen.About.route) {
            AboutRoute(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToGithub = {
                    navController.navigate(Screen.DetailUser.createRoute("myxzlpltk"))
                }
            )
        }
    }
}