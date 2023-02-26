package com.bits.hackathon.studyfocus.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bits.hackathon.studyfocus.Screen
import com.bits.hackathon.studyfocus.screens.*
import com.bits.hackathon.studyfocus.viewmodels.*

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    loginViewModel: LoginViewModel,
    timerViewModel: TimerViewModel,
    rewardsViewModel: RewardsViewModel,
    statisticsViewModel: StatisticsViewModel,
    allSessionsViewModel: AllSessionsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable(route = Screen.Timer.route) {
            TimerScreen(navController = navController, timerViewModel = timerViewModel)
        }
        composable(route = Screen.Rewards.route) {
            RewardsScreen(navController = navController, rewardsViewModel = rewardsViewModel)
        }
        composable(route = Screen.Statistics.route) {
            StatisticsScreen(navController = navController, statisticsViewModel = statisticsViewModel)
        }
        composable(route = Screen.AllSessions.route) {
            AllSessionsScreen(navController = navController, allSessionsViewModel = allSessionsViewModel )
        }
    }
}