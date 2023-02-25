package com.bits.hackathon.studyfocus

sealed class Screen(val route: String) {
    object MainScreen : Screen(route = "mainScreen")
    object Login : Screen(route = "loginScreen")
}