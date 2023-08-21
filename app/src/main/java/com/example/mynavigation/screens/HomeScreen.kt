package com.example.mynavigation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mynavigation.GlobalData
import com.example.mynavigation.R
import com.example.mynavigation.UserAuth
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
                .background(Color(0xFF303233))
                .wrapContentSize(Alignment.Center)
        ) {

            if (!UserAuth.isAuthorization()) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth() ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Зарегистрируйтесь, для просмотра тренировок",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = {
                                navController.navigate(route = "login")
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(top = 10.dp)
                        ) {
                            androidx.compose.material.Text(
                                text = "Перейти на экран регистрации",
                                color = Color.White
                            )
                        }
                    }
                }
                return@Scaffold
            }

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

                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color(0xFFffffff)),
                    modifier = Modifier
//                        .padding(10.dp)
//                        .weight(1f)
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(start = 40.dp, top = 10.dp, end = 40.dp)
                ) {
                    Row(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.main_frontsquat),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable {
                                    val task = entry.key
                                    navController.navigate(route = "home/$task")
                                }
                        )


                    }


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 20.dp, top = 10.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier) {
                        val txt = entry.key
                        Text(
                            text = "Задание $txt ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "| Группа 1", color = Color.White)
                    }
                    Row {
                        Text(text = GlobalData.getDate().toString(), color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.size(30.dp))
            }
        }
    }

}


