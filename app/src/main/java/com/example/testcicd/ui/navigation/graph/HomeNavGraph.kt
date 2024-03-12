package com.example.testcicd.ui.navigation.graph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testcicd.R
import com.example.testcicd.ui.screen.home.catalog.CatalogScreen
import com.example.testcicd.ui.screen.home.main.MainScreen
import com.example.testcicd.ui.screen.home.profile.ProfileScreen

@Composable
fun HomeNavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeNavGraph.Main.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(HomeNavGraph.Main.route) {
            MainScreen()
        }
        composable(HomeNavGraph.Catalog.route) {
            CatalogScreen()
        }
        composable(HomeNavGraph.Profile.route) {
            ProfileScreen()
        }
    }
}

sealed class HomeNavGraph(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    data object Main : HomeNavGraph(Route.MAIN, R.string.main, R.drawable.ic_home)

    data object Catalog: HomeNavGraph(Route.CATALOG, R.string.catalog, R.drawable.ic_catalog)

    data object Profile : HomeNavGraph(Route.PROFILE, R.string.profile, R.drawable.ic_profile)

    object Route {
        const val MAIN = "main"
        const val CATALOG = "catalog"
        const val PROFILE = "profile"
    }
}
