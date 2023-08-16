package com.example.mynavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mynavigation.bars.TopBarCard


@Composable
fun CardViewer(navController: NavHostController, cardName: Int) {

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(Color(0xFF303233))
                .verticalScroll(rememberScrollState())
                .wrapContentSize(Alignment.Center)
                .clickable { focusManager.clearFocus() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            lines?.forEach { (key, message) ->
                var msg by rememberSaveable { mutableStateOf("") }
                if (msg.isEmpty()) msg = message
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(
                                text = key,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(fontSize = 16.sp),
                                modifier = Modifier.padding(top = 20.dp)
                            )
                            TextField(
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .padding(top = 10.dp),
                                shape = RoundedCornerShape(20.dp),
                                singleLine = true,
                                value = msg,
                                onValueChange = {
                                    msg = it
                                    lines!![key] = msg

                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Black,
                                    backgroundColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    cursorColor = Color.Black,
                                    unfocusedIndicatorColor= Color.Transparent
                                ),
                                textStyle = TextStyle(fontSize = 18.sp),
                            )
                        }
                    }
                }
            }

        }

    }
}