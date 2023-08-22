package com.example.mynavigation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.mynavigation.GlobalData
import com.example.mynavigation.MarsApi
import com.example.mynavigation.UserAuth
import com.example.mynavigation.bars.BottomBar
import com.example.mynavigation.globalLoginData
import com.example.mynavigation.navigations.NavGraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MainScreen() {

    var authCall by rememberSaveable { mutableStateOf(false) }

    GlobalData.InitializeData()

    LaunchedEffect(Unit){
        this.launch {
            authCall = GlobalData.checkAuthentication()
        }
    }

    if (!authCall){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFFffffff)),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth(),
                    backgroundColor = Color.LightGray,
                    color = Color(0xFFea7501)
                )
            }
        }
    }else{
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




}