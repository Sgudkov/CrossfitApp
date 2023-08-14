package com.example.mynavigation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mynavigation.GlobalData
import com.example.mynavigation.R
import com.example.mynavigation.bars.TopHomeBar


@Composable
fun HomeScreen(navController: NavHostController) {

    val listExercises = GlobalData.getExercise()[GlobalData.getDate()]

    Scaffold(
        topBar = { TopHomeBar() }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.Black)
                .wrapContentSize(Alignment.Center)
        ) {

            if (listExercises == null) {
                Text(
                    text = "На выбранную дату нет записей",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                return@Scaffold
            }

            listExercises.forEach { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxHeight(),
                ) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(Color(0xFFffffff)),
                        modifier = Modifier
//                        .padding(10.dp)
//                        .weight(1f)
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.TopCenter
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.taskicon),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .size(80.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxSize()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceAround,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val txt = entry.key
                                    Text(
                                        text = "Задание $txt",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .padding(bottom = 20.dp)
                                            .align(alignment = Alignment.Start)
                                    )
                                    Button(
                                        modifier = Modifier
                                            .align(alignment = Alignment.End)
                                            .padding(end = 2.dp),
                                        onClick = {
                                            val task = entry.key
                                            navController.navigate(route = "home/$task")
                                        },
                                        colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                                        border = BorderStroke(0.5.dp, Color(0xFF000000)),
                                    ) {
                                        Text(
                                            text = "Записать результат",
                                            color = Color(0xFFffffff)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(30.dp))
            }
        }
    }

}
