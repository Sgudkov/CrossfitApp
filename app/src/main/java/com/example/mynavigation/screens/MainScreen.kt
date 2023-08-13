package com.example.mynavigation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.mynavigation.GlobalData
import com.example.mynavigation.bars.BottomBar
import com.example.mynavigation.navigations.NavGraph

@Composable
fun MainScreen() {
    GlobalData.initializeData()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController) },
        containerColor = Color.Black
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            NavGraph(navController = navController)
        }
    }
}