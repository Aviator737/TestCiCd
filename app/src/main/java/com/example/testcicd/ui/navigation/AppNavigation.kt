package com.example.testcicd.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.testcicd.ui.screen.home.HomeContainer

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    HomeContainer(navController = navController)
}
