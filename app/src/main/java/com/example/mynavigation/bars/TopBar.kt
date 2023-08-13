package com.example.mynavigation.bars

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mynavigation.GlobalData
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.util.Locale


@Composable
fun TopBar() {
    val dialogState = rememberMaterialDialogState()

//    if (GlobalData.getDate().){
//        GlobalData.setDate(LocalDate.now().toString())
//    }

    val myLocale = Locale("ru", "RU")
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Отмена")
        }

    ) {

        datepicker(
            locale = myLocale,
            title = "Дата тренировки",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = Color(0xFFea7501),
                dateActiveBackgroundColor = Color(0xFFea7501)
            )
        ) { date ->
            // TODO: change date format
            GlobalData.setDate(date)
        }

    }

    TopAppBar(
        title = { Text(text = GlobalData.getDate().toString(), color = Color.White) },
        backgroundColor = Color(0xFF000000),
        actions = {
            IconButton(
                onClick = {
                    dialogState.show()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    tint = Color.White,
                    contentDescription = "Localized description"
                )
            }

        }

    )

}
