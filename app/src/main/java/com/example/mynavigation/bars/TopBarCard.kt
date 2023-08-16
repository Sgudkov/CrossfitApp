package com.example.mynavigation.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.mynavigation.GlobalData
import com.example.mynavigation.navigations.Screens
import com.example.mynavigation.showState

@Composable
fun TopBarCard(
    cardName: Int,
    val1: MutableMap<String, String>,
    navController: NavHostController
) {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
//                        navController.popBackStack(Screens.Card.route, inclusive = true)
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
                Column {
                    Text(
                        text = GlobalData.getDate().toString(),
                        color = Color.White
                    )
                    Text(
                        text = "Задание $cardName",
                        color = Color.White
                    )
                }
                IconButton(
                    onClick = {
                        GlobalData.setTask(key = cardName, value = val1)
                        //TODO call POST method to send change on server
                        showState("Изменено", context = context)
                        navController.popBackStack(Screens.Card.route, inclusive = true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
            }
        },
        backgroundColor = Color(0xFF303233)
    )

}