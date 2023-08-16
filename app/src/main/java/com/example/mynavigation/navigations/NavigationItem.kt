package com.example.mynavigation.navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector


open class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object Home : NavigationItem(Screens.Home.route, Icons.Filled.Home, "home")
    object Login : NavigationItem(Screens.Login.route, Icons.Filled.AccountBox, "login")
    object Detail : NavigationItem(Screens.Detail.route, Icons.Filled.Build, "detail")
    object Profile : NavigationItem(Screens.Profile.route, Icons.Filled.Build, "profile")
}
