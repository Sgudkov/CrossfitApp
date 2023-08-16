package com.example.mynavigation.screens

import android.app.Activity
import android.util.Log
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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material.TextField
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
import com.example.mynavigation.GlobalData
import com.example.mynavigation.MarsApi
import com.example.mynavigation.PreferencesManager
import com.example.mynavigation.UserAuth
import com.example.mynavigation.globalLoginData
import com.example.mynavigation.showState
import kotlinx.coroutines.launch


@Composable
fun LoginScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var radioButton by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current as Activity
    val response = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val body by remember { mutableStateOf(mutableMapOf("email" to "")) }
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getData(" ", "")) }

    body["password"] = ""

//    showState("Call after register", context)

    if (UserAuth.isAuthorization()) {
        LoggedScreen()
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
            onValueChange = { email = it },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedIndicatorColor= Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Пароль") },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedIndicatorColor= Color.Transparent
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
                colors = RadioButtonDefaults.colors( Color(0xFFea7501)),
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
                colors = RadioButtonDefaults.colors( Color(0xFFea7501)),
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
                        coroutineScope.launch {
                            body["email"] = email
                            body["password"] = password
                            UserAuth.setUserAuthorization(true)
                            try {
                                val listResult = MarsApi.retrofitService.postRegister(body)
                                response.value = listResult.toString()
                                if (listResult.raw().code() == 500) {
                                    Log.e(
                                        "MYTEST",
                                        "Register error = ${listResult.raw().message()}"
                                    )
                                } else {
                                    Log.i("MYTEST", "Register success = $response.value")
                                }

                            } catch (e: Exception) {
                                Log.e("MYTEST", "Register failure = $e.message")
                            }

                        }

                        preferencesManager.saveData(email, password)
                        data.value = password
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(text = "Регистрация")
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
                    Text(text = "Вход")
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

}
