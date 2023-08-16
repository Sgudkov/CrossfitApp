package com.example.mynavigation.navigations

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mynavigation.CardViewer
import com.example.mynavigation.screens.DetailScreen
import com.example.mynavigation.screens.HomeScreen
import com.example.mynavigation.screens.LoginScreen
import com.example.mynavigation.screens.ProfileScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,
        modifier = Modifier
    )
    {

        composable(route = Screens.Login.route) {
            LoginScreen(navController)
        }

        composable(route = Screens.Home.route) {
            HomeScreen(navController)
        }

        composable(route = Screens.Detail.route) {
            DetailScreen()
        }

        composable(
            route = Screens.Card.route,
            arguments = listOf(navArgument("task") { defaultValue = 0 })
        ) {
            it.arguments?.getInt("task")?.let { it1 -> CardViewer(navController, it1) }
        }

    }
//    MyBackHandler()
}


@Composable
fun MyBackHandler() {
    val activity = LocalContext.current as? Activity
    BackHandler {
        activity?.finish()
    }
}