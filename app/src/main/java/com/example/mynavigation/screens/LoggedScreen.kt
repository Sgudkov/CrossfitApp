package com.example.mynavigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mynavigation.LoggedCard
import com.example.mynavigation.R


@Composable
fun LoggedScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Row(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
        ) {
            LoggedCard(R.drawable.female, true)
        }
        Row(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
        ) {
            LoggedCard(R.drawable.male)
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column {
                    Text(text = "Данные 1")
                    Text(text = "Данные 2")
                }

            }
        }



    }

}