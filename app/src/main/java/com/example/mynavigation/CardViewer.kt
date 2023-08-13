package com.example.mynavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mynavigation.bars.TopBarCard


@Composable
fun CardViewer(navController: NavHostController, cardName: String = "") {

    val lines by remember {
        mutableStateOf(
            GlobalData.getExercise()[GlobalData.getDate()]?.get(
                cardName
            )
        )
    }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { lines?.let { TopBarCard(cardName, it, navController) } }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(Color.Black)
                .wrapContentSize(Alignment.Center)
                .clickable { focusManager.clearFocus() }
        ) {
            items(1) {
                lines?.forEach { (key, message) ->
                    var msg by rememberSaveable { mutableStateOf("") }
                    if (msg.isEmpty()) msg = message
                    Text(
                        text = key,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(fontSize = 30.sp)
                    )
                    TextField(
                        value = msg,
                        onValueChange = {
                            msg = it
                            lines!![key] = msg

                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.White
                        ),
                        textStyle = TextStyle(fontSize = 18.sp),
                    )
                }
            }
        }

    }
}