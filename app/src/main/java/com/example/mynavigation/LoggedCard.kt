package com.example.mynavigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp



@Composable
fun LoggedCard(sourceImg: Int, userProfile: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = sourceImg),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
//                        .clip(CircleShape)
                        .align(alignment = Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                if (userProfile){
                    Text(text = "Имя")
                    Text(text = "Фамилия")
                    Text(text = "Дата начала занятий")
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                            border = BorderStroke(0.5.dp, Color(0xFF000000)),
                        ) {
                            Text(text = "Редактировать", color = Color(0xFFffffff))
                        }
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                            border = BorderStroke(0.5.dp, Color(0xFF000000)),
                        ) {
                            Text(text = "Выйти", color = Color(0xFFffffff))
                        }
                    }
                }else{
                    Text(text = "Нет записи к тренеру")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                            border = BorderStroke(0.5.dp, Color(0xFF000000)),
                        ) {
                            Text(text = "Записаться к тренеру", color = Color(0xFFffffff))
                        }
                    }
                }

            }
        }

    }
}