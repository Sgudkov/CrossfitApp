package com.example.mynavigation.bars

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mynavigation.navigations.NavigationItem


@Composable
fun BottomBar(navController: NavHostController) {

    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Login,
//        NavigationItem.Detail,
    )

    BottomNavigation(backgroundColor = Color(0xFF303233)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->

            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = "") },
//                label = { Text(text = if(currentRoute == item.route) item.route  else "") },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = item.route.let {
                    currentRoute?.contains(it, ignoreCase = true) ?: false
                },
//                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                    {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
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

