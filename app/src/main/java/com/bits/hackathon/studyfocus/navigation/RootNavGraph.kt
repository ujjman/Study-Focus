package com.bits.hackathon.studyfocus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bits.hackathon.studyfocus.Screen
import com.bits.hackathon.studyfocus.screens.MainScreen
import com.bits.hackathon.studyfocus.viewmodels.MainViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }
}