package com.bits.hackathon.studyfocus

sealed class Screen(val route: String) {
    object MainScreen : Screen(route = "mainScreen")
    object Login : Screen(route = "loginScreen")
    object Timer : Screen(route = "timerScreen")
    object Rewards : Screen(route = "rewardsScreen")
    object Statistics : Screen(route = "statisticsScreen")
    object AllSessions : Screen(route = "allStatisticsScreen")
    object Note : Screen(route = "noteScreen")
}