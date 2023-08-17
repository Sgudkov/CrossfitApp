package com.example.mynavigation.screens

import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
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
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController?, profile: Boolean) {

    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    val trainer by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var cam by remember { mutableStateOf(false) }


    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    var openDialog by remember { mutableStateOf(true) }

    if (openDialog && !cameraPermissionState.status.isGranted) {
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
                        if (!cameraPermissionState.status.isGranted) {
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

    @androidx.camera.core.ExperimentalGetImage
    if (cam) SimpleCameraPreview()

}

@androidx.camera.core.ExperimentalGetImage
@Composable
fun SimpleCameraPreview() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = cameraProviderFuture.get()

    var closeCamera by remember {
        mutableStateOf(false)
    }


    if (closeCamera) {
        ProfileScreen(navController = null, profile = false)
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = {
                                cameraProvider?.unbindAll()
                                closeCamera = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                tint = Color.White,
                                contentDescription = " "
                            )
                        }
                    }
                },
                backgroundColor = Color(0xFF303233),


                )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx).also {
                        it.scaleType = PreviewView.ScaleType.FILL_CENTER
                        it.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                        it.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    }
                    val executor = ContextCompat.getMainExecutor(ctx)

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .apply {
                            setAnalyzer(executor, BarcodeAnalyser { barcodeValue  ->
//                                cameraProvider?.unbindAll()
//                                closeCamera = true
                                barcodeValue.forEach{barcode ->
                                    Toast.makeText(ctx, barcode.rawValue.toString(), Toast.LENGTH_SHORT).show()
                                }


                            })
                        }

                    val imageCapture = ImageCapture.Builder().build()

                    cameraProviderFuture.addListener({

                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()

                        cameraProvider?.unbindAll()
                        cameraProvider?.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            imageCapture,
                            imageAnalysis,
                            preview
                        )
                    }, executor)
                    previewView
                },
                modifier = Modifier


            )
        }
    }

}

@androidx.camera.core.ExperimentalGetImage
class BarcodeAnalyser(
//    val callback: (Any?) -> Unit
    private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {

    private var lastAnalyzedTimeStamp = 0L
    override fun analyze(imageProxy: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimeStamp >= TimeUnit.SECONDS.toMillis(1)) {
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()

            val scanner = BarcodeScanning.getClient(options)
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

                scanner.process(image)
                    .addOnSuccessListener { barcodes ->

                        if (barcodes.size > 0) {
                            onBarcodeDetected(barcodes)
                        }
                    }
                    .addOnFailureListener {

                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            }
            lastAnalyzedTimeStamp = currentTimestamp
        } else {
            imageProxy.close()
        }
    }

}

