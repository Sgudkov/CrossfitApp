package com.example.mynavigation.screens

import android.app.Activity
import android.util.Log
import android.util.Patterns
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mynavigation.EmailDigits
import com.example.mynavigation.GlobalData
import com.example.mynavigation.MarsApi
import com.example.mynavigation.PreferencesManager
import com.example.mynavigation.UserAuth
import com.example.mynavigation.UserEmailVerifyModel
import com.example.mynavigation.globalLoginData
import com.example.mynavigation.showState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var radioButton by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current as Activity
    val response = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val body by remember { mutableStateOf(mutableMapOf("email" to "")) }
    val preferencesManager = remember { PreferencesManager(context) }
//    val data = remember { mutableStateOf(preferencesManager.getData(" ", "")) }
    var openNumbers by remember { mutableStateOf(false) }
    var passError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    if (UserAuth.isAuthorization()) {
        ProfileScreen(navController)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() }
            .background(Color(0xFF303233)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            label = { Text(text = "Эл.Почта") },
            value = email,
            singleLine = true,
            isError = emailError,
            onValueChange = {
                email = it
                emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Пароль") },
            value = password,
            singleLine = true,
            isError = passError,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                passError = password.isEmpty()
                password = it
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            Modifier
                .selectableGroup()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = radioButton,
                colors = RadioButtonDefaults.colors(Color(0xFFea7501)),
                onClick = {
                    radioButton = true
                    GlobalData.setAthlete(true)
                },
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Атлет",
                fontSize = 22.sp,
                color = Color.White
            )
            RadioButton(
                selected = !radioButton,
                colors = RadioButtonDefaults.colors(Color(0xFFea7501)),
                onClick = {
                    radioButton = false
                    GlobalData.setAthlete(false)
                },
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Тренер",
                fontSize = 22.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)

        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        //Registration
                        passError = password.isEmpty()
                        emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()

                        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
                                .matches() && password.isNotEmpty()
                        ) {
                            coroutineScope.launch {
                                body["email"] = email
                                body["password"] = password
                                try {
                                    val listResult = MarsApi.retrofitService.postRegister(body)
                                    response.value = listResult.toString()
                                    if (listResult.raw().code() == 500) {
                                        showState("Ошибка, попробуйте позже", context)
                                        Log.e(
                                            "MYTEST",
                                            "Register error = ${listResult.raw().message()}"
                                        )
                                    } else {

                                        val responseLogin = MarsApi.retrofitService.postLogin(body)
                                        response.value = responseLogin.accesstoken.toString()

                                        if (response.value.isEmpty()) {
                                            showState(
                                                "Что-то пошло не так, попробуйте позже",
                                                context
                                            )
                                        } else {
                                            preferencesManager.saveData(email, password)
                                            preferencesManager.saveData(
                                                "access_token",
                                                response.value
                                            )
                                            globalLoginData.setToken(responseLogin.accesstoken.toString())
                                            //Open for email code verification
                                            openNumbers = true
                                        }

                                        Log.i("MYTEST", "Register success = $response.value")
                                    }

                                } catch (e: Exception) {
                                    showState("Ошибка сервера, попробуйте позже", context)
                                    Log.e("MYTEST", "Register failure = $e.message")
                                }

                            }
                        } else {
                            showState("Заполните обязательные поля", context)
                        }

                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(text = "Регистрация", color = Color.White)
                }
                Button(
                    onClick = {
                        body["email"] = email
                        body["password"] = password
                        coroutineScope.launch {
                            try {
                                val listResult = MarsApi.retrofitService.postLogin(body)
                                response.value = listResult.accesstoken.toString()
                                if (listResult.accesstoken.toString().isEmpty()) {
                                    Log.e("MYTEST", "Login error ")
                                } else {
                                    globalLoginData.setToken(listResult.accesstoken.toString())
                                    Log.i("MYTEST", "Login success = $response.value")
                                    Log.i("MYTEST", "Login token = ${globalLoginData.getToken()}")
                                }

                                //TODO save to shared Preferance login + token
                            } catch (e: Exception) {
                                response.value = "Failure: ${e.message}"
                                Log.e("MYTEST", "Login failure = $response.value")
                                showState(response.value, context)
                            }
                        }


                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(text = "Вход", color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Забыли пароль?"),
            onClick = {
                val pref = preferencesManager.getData(email, "")
                showState("Password = $pref", context)

            },
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.White
            )
        )
    }

    if (openNumbers) {
        openNumbers = callNumberFromEmail(body)
    }

}


@Composable
fun callNumberFromEmail(body: Map<String, String>): Boolean {

    var openDialog by remember { mutableStateOf(true) }
    var emailCode by remember { mutableStateOf("") }
    var emailVerify by remember { mutableStateOf("") }
    var callProgress by remember { mutableStateOf(false) }
    var requestVerify by remember { mutableStateOf(false) }
    var listResult by remember { mutableStateOf(UserEmailVerifyModel("")) }
    var emailCodeError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val maxChar = 4
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { },
            backgroundColor = Color.White,
            shape = RoundedCornerShape(20.dp),
            title = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    if (callProgress) {
                        Text(
                            text = "Проверяем код",
                            color = Color.Black
                        )
                        Box(modifier = Modifier.padding(15.dp)) {
                            CustomLinearProgressBar()
                        }
                    } else {
                        Text(
                            text = "Введите четырехзначный код отправленный на указанный e-mail",
                            color = Color.Black
                        )
                        TextField(
                            value = emailCode,
                            onValueChange = {
                                if (it.length <= maxChar) {
                                    emailCode = it
                                    if ((it.length) == maxChar) EmailDigits.setDigit(emailCode)
                                }
                                emailCodeError = false
                            },
                            modifier = Modifier.weight(1f).padding(10.dp),
                            shape = RoundedCornerShape(20.dp),
                            singleLine = true,
                            isError = emailCodeError,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }


                }

            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                    onClick = {
                        callProgress = true
                        coroutineScope.launch {
                            try {
                                listResult = MarsApi.retrofitService.getEmailVerify(body)

                                if (listResult.emailVerify.toString().isEmpty()) {
                                    showState("Что-то пошло не так, попробуйте позже", context)
                                    Log.e("MYTEST", "Login error ")
                                } else {
                                    emailVerify = listResult.emailVerify.toString()
                                }

                            } catch (e: Exception) {
                                emailVerify = "asd"
                                Log.e("MYTEST", "Login failure = ${e.message}")
                                showState("Что-то пошло не так, попробуйте позже", context)
                            }
                            requestVerify = true
                            //TODO delete delay
                            withContext(Dispatchers.IO) {
                                Thread.sleep(5_000)
                            }

                            if (listResult.emailVerify.toString() == EmailDigits.getDigit() && EmailDigits.getDigit()
                                    .isNotEmpty()
                            ) {
                                UserAuth.setUserAuthorization(true)
                                openDialog = false
                            } else {
                                emailCodeError = true
                                callProgress = false
                                showState("Введенный код неверный", context)
                            }

                        }

                    }) {

                    androidx.compose.material.Text(
                        text = "Подтвердить",
                        color = Color.White
                    )

                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                    onClick = {
                        openDialog = false
                        emailCode = ""
                        EmailDigits.setDigit("")
                    }) {
                    androidx.compose.material.Text(
                        text = "Отменить",
                        color = Color.White
                    )
                }

            }
        )

    }

    return openDialog
}


@Composable
private fun CustomLinearProgressBar() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(Color(0xFFffffff)),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .height(25.dp),
                backgroundColor = Color.LightGray,
                color = Color(0xFFea7501)
            )
        }

    }
}