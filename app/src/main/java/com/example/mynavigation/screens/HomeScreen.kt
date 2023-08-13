package com.example.mynavigation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mynavigation.GlobalData
import com.example.mynavigation.bars.TopBar


@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(
        topBar = { TopBar() }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(Color.Black)
                .wrapContentSize(Alignment.Center)
        ) {
            items(1) {
                GlobalData.getExercise()[GlobalData.getDate()]?.forEach { entry ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(Color(0xFFffffff)),
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxHeight()
                        //                        .height(120.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Text(
                                        text = entry.key,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    //                                    Text(
                                    //                                        text = entry.value,
                                    //                                        color = Color.Black
                                    //                                    )
                                }

                                Button(
                                    onClick = {
                                        val task = entry.key
                                        navController.navigate(route = "home/$task")
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                                    border = BorderStroke(0.5.dp, Color(0xFF000000)),
                                    modifier = Modifier
                                        .align(alignment = Alignment.End)
                                    //                                .padding(end = 5.dp)
                                ) {
                                    Text(text = "Записать результат", color = Color(0xFFffffff))
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                }
            }
        }
    }

}
