package com.example.mynavigation.navigations


sealed class Screens(val route: String) {
    object Login: Screens("login")
    object Home: Screens("home")
    object Detail: Screens("detail")
    object Card: Screens("home/{task}")
    object Profile: Screens("login/{profile}")
    object QR: Screens("qr")

}

