package com.bits.hackathon.studyfocus

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bits.hackathon.studyfocus.OverlayStateHolder.checkForTimerComplete
import com.bits.hackathon.studyfocus.OverlayStateHolder.countdownHr
import com.bits.hackathon.studyfocus.OverlayStateHolder.countdownMin
import com.bits.hackathon.studyfocus.OverlayStateHolder.countdownSeconds
import com.bits.hackathon.studyfocus.OverlayStateHolder.durationSeconds
import com.bits.hackathon.studyfocus.data.SessionDetails
import com.bits.hackathon.studyfocus.viewmodels.TimerViewModel
import org.checkerframework.checker.units.qual.min
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
class OverlayComponent(
    private val context: Context, private val stopService: () -> Unit
) {

    private val player: MediaPlayer
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var startTime: String = ""
    private var endTime: String = ""
    private var lengthOfSession: Int = 0
    private var formatter: DateTimeFormatter? = null
    private var date: String = ""
    private var completeOrIncomplete: String = ""
    var isTimerOverlayShowing = false

    private val clickTargetOverlay = OverlayViewHolder(
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            0, // todo place default position
            0,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.OPAQUE
        ), context
    )

    private val fullscreenOverlay = OverlayViewHolder(
        WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.OPAQUE
        ), context
    )

    init {
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        player = MediaPlayer.create(context, alarmSound)
        player.isLooping = true
        fullscreenOverlay.params.alpha = 1f
        val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            fullscreenOverlay.params.alpha = inputManager.maximumObscuringOpacityForTouch
        }
        setContentClickTargetOverlay()
        setContentFullscreenOverlay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showOverlay() {
        if (isTimerOverlayShowing) {
            return
        }
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        startTime = LocalDateTime.now().format(formatter)
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        date = LocalDateTime.now().format(formatter)
        windowManager.addView(clickTargetOverlay.view, clickTargetOverlay.params)
        windowManager.addView(fullscreenOverlay.view, fullscreenOverlay.params)
        isTimerOverlayShowing = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun endService() {

        player.pause()
        createSession()
        windowManager.removeView(clickTargetOverlay.view)
        windowManager.removeView(fullscreenOverlay.view)


        fullscreenOverlay.view.disposeComposition()
        clickTargetOverlay.view.disposeComposition()
        isTimerOverlayShowing = false
        stopService()
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setContentClickTargetOverlay() {
        clickTargetOverlay.view.setContent {
            var showDialogToCancel by remember {
                mutableStateOf(false)
            }
            var showDialogForDone by remember {
                mutableStateOf(false)
            }
            var totsec: Int = durationSeconds
            var totSeconds = durationSeconds
            var second by remember {
                mutableStateOf(countdownSeconds)
            }
            var minute by remember {
                mutableStateOf(countdownMin)
            }
            var hour by remember {
                mutableStateOf(countdownHr)
            }
            var progressValue by remember {
                mutableStateOf(totsec*1f)
            }
            val progress by animateFloatAsState(progressValue)
            LaunchedEffect(Unit) {
                val timer = object : CountDownTimer(totsec * 1000L, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        totsec--
                        progressValue=progressValue.minus(1f)
                        hour = totsec / 3600
                        minute = (totsec / 60) - (hour * 60)
                        if (second - 1 == -1) second = 59
                        else second--
                        lengthOfSession++

                    }

                    override fun onFinish() {
                        player.start()
                        showDialogForDone = true
                        formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                        endTime = LocalDateTime.now().format(formatter)
                    }
                }.start()
            }

            var progressMaxValue by remember {
                mutableStateOf(totSeconds*1f)
            }

            val progressMax by animateFloatAsState(progressMaxValue)
                Scaffold(
                    backgroundColor = Color.White
                ) {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.padding(top=50.dp))
                       CircularProgressBar(
                            modifier = Modifier.size(250.dp),
                            progress = progress,
                            progressMax = progressMax,
                            progressBarColor = Color.Blue,
                            progressBarWidth = 10.dp,
                            backgroundProgressBarColor = Color.LightGray,
                            backgroundProgressBarWidth = 10.dp,
                            roundBorder = true,
                            startAngle = 360f,
                            hour,minute,second
                        )

                        Spacer(modifier = Modifier.padding(top = 20.dp))


                        when (showDialogForDone) {

                            true -> {
                                createNotificationChannel()
                                checkForTimerComplete = true
                                Button(
                                    onClick = {
                                        cancelNotification(context, 1234)
                                        completeOrIncomplete = "complete"
                                        endService()
                                    },
                                    modifier = Modifier
                                        .align(CenterHorizontally)
                                        .padding(10.dp)
                                        .width(150.dp)
                                        .height(60.dp)

                                ) {
                                    Text(text = "Done")
                                }

                            }

                            false -> {
                                when (showDialogToCancel) {
                                    false -> Button(
                                        onClick = {
                                            showDialogToCancel = true
                                        },
                                        modifier = Modifier
                                            .align(CenterHorizontally)
                                            .width(150.dp)
                                            .height(60.dp)

                                    ) {
                                        Text(text = "Cancel")
                                    }
                                }
                                when (showDialogToCancel) {
                                    true -> {
                                        Text(
                                            text = "Are you sure to cancel?",
                                            fontSize = 18.sp,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(20.dp)
                                        ) {
                                            Button(
                                                onClick = {
                                                    formatter =
                                                        DateTimeFormatter.ofPattern("HH:mm:ss")
                                                    endTime = LocalDateTime.now().format(formatter)
                                                    completeOrIncomplete = "incomplete"
                                                    endService()
                                                }, modifier = Modifier.padding(20.dp)
                                            ) {
                                                Text(text = "Yes")
                                            }
                                            Button(
                                                onClick = { showDialogToCancel = false },
                                                modifier = Modifier.padding(20.dp)
                                            ) {
                                                Text(text = "No")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    private fun setContentFullscreenOverlay() {
        fullscreenOverlay.view.setContent {
            square()
        }
    }

    private fun share() {

        val dataToShare =
            "Pomodoro Timer \nStarting Time : $startTime \nEnd Time : $endTime \nLength of session : $lengthOfSession \nDate : $date"

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, dataToShare)
        sharingIntent.type = "text/plain"

        val chooserIntent = Intent.createChooser(sharingIntent, "Share To:")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(chooserIntent)
    }

    private fun cancelNotification(ctx: Context, notifyId: Int) {
        val ns = NOTIFICATION_SERVICE
        val nMgr = ctx.getSystemService(ns) as NotificationManager
        nMgr.cancel(notifyId)
    }

    @Composable
    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Pomodoro Timer"
            val descriptionText = "Timer ended"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("mishra", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder =
            NotificationCompat.Builder(context, "mishra").setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Timer ended")
                .setContentText("Click on Done to remove the timer screen")
                .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent)
                .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(1234, builder.build())
        }
    }

    private fun createSession() {
        val session: SessionDetails =
            SessionDetails(startTime, endTime, lengthOfSession, date, completeOrIncomplete)
        addSession(session)

    }

    @Composable
    fun square() {
        Surface(
            modifier = Modifier.fillMaxSize(), color = Color.Transparent
        ) {}
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
    startAngle: Float = 360f,
    hr: Int,
    min: Int,
    sec: Int
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()

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
            color = Color.Black,
            fontSize = 35.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

