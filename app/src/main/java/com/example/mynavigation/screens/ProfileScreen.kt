package com.example.mynavigation.screens

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.mynavigation.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, profile: Boolean) {

    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var trainer by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var cam by remember { mutableStateOf(false) }


    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    var openDialog by remember { mutableStateOf(true) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = { },
            backgroundColor = Color.Black,
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    text = "Для записи к тренеру нужен доступ к камере",
                    color = Color.White
                )
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                    onClick = { openDialog = false }
                ) {
                    Text(
                        text = "Отказаться",
                        color = Color.White
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                    onClick = {
                        if (!cameraPermissionState.status.isGranted){
                            cameraPermissionState.launchPermissionRequest()
                        }
                        openDialog = false
                    }) {
                    Text(
                        text = "Подтвердить",
                        color = Color.White
                    )
                }

            }
        )

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF303233))
            .clickable { focusManager.clearFocus() }
            .wrapContentSize(Alignment.TopCenter),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(165.dp)
                    .clip(CircleShape)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Column {
                        Text(
                            text = "Полное имя",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(top = 20.dp)
                        )
                        TextField(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            shape = RoundedCornerShape(20.dp),
                            singleLine = true,
                            value = name,
                            onValueChange = { name = it },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 18.sp),
                        )
                    }
                }

                Row {
                    Column {
                        Text(
                            text = "Телефон",
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
                            value = phone,
                            onValueChange = { phone = it },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 18.sp),
                        )
                    }
                }

                Row {
                    Column {
                        Text(
                            text = "Эл. Почта",
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
                            readOnly = true,
                            value = email,
                            onValueChange = { email = it },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = TextStyle(fontSize = 18.sp),
                        )
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (trainer.isEmpty()) {
                        Button(
                            onClick = {
                                if (cameraPermissionState.status.isGranted) cam = true
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFFea7501)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = "Записаться к тренеру",
                                color = Color.White
                            )
                        }
                    } else {
                        Text(
                            text = "Ваш тренер: $trainer",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }
            }
        }


    }

    if (cam) SimpleCameraPreview()

}


@Composable
fun SimpleCameraPreview() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview
                )
            }, executor)
            previewView
        },
        modifier = Modifier.fillMaxSize(),
    )
}

