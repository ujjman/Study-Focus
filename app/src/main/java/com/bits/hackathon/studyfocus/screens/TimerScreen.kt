package com.bits.hackathon.studyfocus.screens

import com.bits.hackathon.studyfocus.viewmodels.MainViewModel


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.PopupProperties
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.bits.hackathon.studyfocus.*
import com.bits.hackathon.studyfocus.R
import com.bits.hackathon.studyfocus.viewmodels.TimerViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun TimerScreen(
    navController: NavHostController, timerViewModel: TimerViewModel
) {
    var progressValue by remember {
        mutableStateOf(100f)
    }
    var hr by remember {
        mutableStateOf(0)
    }
    var min by remember {
        mutableStateOf(0)
    }
    var sec by remember {
        mutableStateOf(0)
    }
    timerViewModel.hour.observe(navController.context as LifecycleOwner, Observer {
        hr = it
    })
    timerViewModel.minute.observe(navController.context as LifecycleOwner, Observer {
        min = it
    })
    timerViewModel.second.observe(navController.context as LifecycleOwner, Observer {
        sec = it
    })
    when(timerViewModel.showOverlayDialog)
    {
        true->
        {
            startTimer(context = navController.context, timerViewModel = timerViewModel)
        }
    }

        when (OverlayStateHolder.checkForTimerComplete) {
            true -> {
                navController.navigate(Screen.Rewards.route)
                OverlayStateHolder.checkForTimerComplete=false
            }
        }

    val progress by animateFloatAsState(progressValue)
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.secondary
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 30.dp))
                CircularProgressBar(
                    modifier = Modifier.size(250.dp),
                    progress = progress,
                    progressMax = 100f,
                    progressBarColor = Blue,
                    progressBarWidth = 10.dp,
                    backgroundProgressBarColor = LightGray,
                    backgroundProgressBarWidth = 10.dp,
                    roundBorder = true,
                    startAngle = 360f,
                    timerViewModel = timerViewModel,
                    hr, min, sec
                )
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(
                    text = "Tap circle above to set the timer",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.padding(top = 50.dp))

                OutlinedButton(
                    onClick = {
                        if (!Settings.canDrawOverlays(navController.context)) {
                            timerViewModel.showOverlayDialog = true
                            return@OutlinedButton
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            createTimer(navController.context, hr, min, sec)
                        }
                    },
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        backgroundColor = Color(62, 111, 168, 255)
                    )
                ) {
                    // Adding an Icon "Add" inside the Button
                    Text(text = "Go", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.padding(top = 90.dp))

                Button(
                    onClick = {
                        timerViewModel.setTime(0, 0, 0)
                    },
                    modifier = Modifier
                        .width(180.dp)
                        .height(80.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        backgroundColor = Color(62, 111, 168, 255)
                    )
                ) {
                    // Adding an Icon "Add" inside the Button
                    Text(text = "Reset", fontSize = 30.sp)
                }

                when (timerViewModel.showGrantOverlayDialog) {

                    true -> {
                        setTime(context = navController.context, timerViewModel = timerViewModel)
                    }
                }

            }
        }
    }
}


@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    progressMax: Float = 100f,
    progressBarColor: Color = Color.Black,
    progressBarWidth: Dp = 7.dp,
    backgroundProgressBarColor: Color = Color.Gray,
    backgroundProgressBarWidth: Dp = 3.dp,
    roundBorder: Boolean = false,
    startAngle: Float = 0f,
    timerViewModel: TimerViewModel,
    hr: Int,
    min: Int,
    sec: Int
) {

    val interactionSource = remember { MutableInteractionSource() }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                timerViewModel.showGrantOverlayDialog = true
            }
    )
    {
        var radius by remember {
            mutableStateOf(0f)
        }
        Canvas(modifier = modifier.fillMaxSize()) {

            val canvasSize = size.minDimension
            Log.d("ssss", size.minDimension.toString())
            radius =
                canvasSize / 2 - maxOf(backgroundProgressBarWidth, progressBarWidth).toPx() / 2
            drawCircle(
                color = backgroundProgressBarColor,
                radius = radius,
                center = size.center,
                style = Stroke(width = backgroundProgressBarWidth.toPx())
            )

            drawArc(
                color = progressBarColor,
                startAngle = 270f + startAngle,
                sweepAngle = (progress / progressMax) * 360f,
                useCenter = false,
                topLeft = size.center - Offset(radius, radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(
                    width = progressBarWidth.toPx(),
                    cap = if (roundBorder) StrokeCap.Round else StrokeCap.Butt
                )
            )
        }
        Text(
            text = "${hr}:${min}:${sec}",
            color = Black,
            fontSize = 35.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun setTime(context: Context, timerViewModel: TimerViewModel) {
    var hr by remember { mutableStateOf("00") }
    var min by remember { mutableStateOf("00") }
    var sec by remember { mutableStateOf("00") }
    AlertDialog(onDismissRequest = {
        timerViewModel.showGrantOverlayDialog = false
    }, confirmButton = {
        Button(onClick = {
            timerViewModel.setTime(hr = hr.toInt(), min = min.toInt(), sec = sec.toInt())
            timerViewModel.showGrantOverlayDialog = false
        }) {
            Text("Save")
        }
    },
        dismissButton = {
            Button(onClick = {
                timerViewModel.showGrantOverlayDialog = false
            }) {
                Text("Cancel")
            }
        },
        title = {
            Text("Set Timer", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }, text = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    keyboardOptions =
                    KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    value = hr,
                    singleLine = true,
                    onValueChange = {
                        if (it.isEmpty()) {
                            hr = it
                        } else if (it.isNotEmpty() && timerViewModel.checkHr(it.toInt())) {
                            hr = it
                        }
                    },
                    textStyle = TextStyle(color = Black, fontSize = 20.sp),
                    label = { Text("hr") },
                    placeholder = { Text(text = "hh") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = White,
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Black,
                        cursorColor = Black,
                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black
                    )
                )
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    keyboardOptions =
                    KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    value = min,
                    singleLine = true,
                    onValueChange = {
                        if (it.isEmpty()) {
                            min = it
                        } else if (it.isNotEmpty() && timerViewModel.checkMin(it.toInt())) {
                            min = it
                        }
                    },
                    textStyle = TextStyle(color = Black, fontSize = 20.sp),
                    label = { Text("min") },
                    placeholder = { Text(text = "mm") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = White,
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Black,
                        cursorColor = Black,
                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black
                    )
                )
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    keyboardOptions =
                    KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    value = sec,
                    singleLine = true,
                    onValueChange = {
                        if (it.isEmpty()) {
                            sec = it
                        } else if (it.isNotEmpty() && timerViewModel.checkSec(it.toInt())) {
                            sec = it
                        }

                    },
                    textStyle = TextStyle(color = Black, fontSize = 20.sp),
                    label = { Text("sec") },
                    placeholder = { Text(text = "ss") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = White,
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Black,
                        cursorColor = Black,
                        focusedBorderColor = Black,
                        unfocusedBorderColor = Black
                    )
                )
            }
        })
}

@Composable
fun startTimer(context: Context, timerViewModel: TimerViewModel) {
    AlertDialog(onDismissRequest = {
        timerViewModel.showOverlayDialog = false
    }, confirmButton = {
        Button(onClick = {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.packageName)
            )

            ActivityCompat.startActivityForResult(
                context as Activity, intent, 1, null
            )
            timerViewModel.showOverlayDialog = false

        }) {
            Text("Go To Settings")
        }
    }, title = {
        Text("Enable Overlay Permission")
    }, text = {
        Text(buildAnnotatedString {
            append("Please enable \"Allow display over other apps\" permission for application ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Study Focus")
            }
        })
    })
}

@RequiresApi(Build.VERSION_CODES.O)
fun createTimer(context: Context, hr: Int, min: Int, sec: Int) {

    val intent = Intent(context.applicationContext, ForegroundService::class.java)
    intent.putExtra("timer", "create")
    intent.putExtra("sec", sec)
    intent.putExtra("min", min)
    intent.putExtra("hr", hr)
    context.startForegroundService(intent)
}