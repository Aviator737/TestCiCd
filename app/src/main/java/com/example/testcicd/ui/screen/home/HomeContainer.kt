package com.example.testcicd.ui.screen.home

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testcicd.ui.navigation.graph.HomeNavGraph
import com.example.testcicd.ui.theme.AppTheme

@Composable
fun HomeContainer(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.shadow(10.dp),
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                listOf(
                    HomeNavGraph.Main,
                    HomeNavGraph.Catalog,
                    HomeNavGraph.Profile,
                ).forEach { screen ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = AppTheme.colors.material.primary,
                            indicatorColor = AppTheme.colors.material.primaryContainer,
                            selectedIconColor = AppTheme.colors.material.onPrimaryContainer
                        ),
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = stringResource(screen.title)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(screen.title)
                            )
                        },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HomeNavGraph(navController = navController, innerPadding = innerPadding)
    }
}
