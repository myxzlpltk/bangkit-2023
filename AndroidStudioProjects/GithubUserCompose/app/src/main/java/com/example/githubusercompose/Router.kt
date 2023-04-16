package com.example.githubusercompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubusercompose.features.dashboard.DashboardRoute
import com.example.githubusercompose.features.detail_user.DetailUserRoute

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object DetailUser : Screen("users/{login}") {
        fun createRoute(login: String) = "users/$login"
    }
}

@Composable
fun Router(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardRoute()
        }
        composable(
            route = Screen.DetailUser.route,
            arguments = listOf(navArgument("login") {
                type = NavType.StringType
            })
        ) {
            val login = it.arguments?.getString("login") ?: ""
            DetailUserRoute()
        }
    }
}