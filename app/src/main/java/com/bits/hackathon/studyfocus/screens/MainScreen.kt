package com.bits.hackathon.studyfocus.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bits.hackathon.studyfocus.R
import com.bits.hackathon.studyfocus.Screen
import com.bits.hackathon.studyfocus.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import org.checkerframework.checker.units.qual.m

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    var mAuth = FirebaseAuth.getInstance()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 80.dp))
                Text(text = "Welcome, ${mAuth.currentUser?.displayName}", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Spacer(modifier = Modifier.padding(20.dp))
                val padding = 10.dp
                Card(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10),
                    contentColor = Color.Black,
                    backgroundColor = Color.Green,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxWidth(0.9f)
                        .drawWithContent {
                            val paddingPx = with(density) { padding.toPx() }
                            clipRect(
                                left = -paddingPx,
                                top = 0f,
                                right = size.width + paddingPx,
                                bottom = size.height + paddingPx
                            ) {
                                this@drawWithContent.drawContent()
                            }
                        }
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column() {
                                Text(text = "Pomodoro Timer for", fontSize = 20.sp, maxLines = 2, fontWeight = FontWeight.Bold)
                                Text(text = "better productivity.", fontSize = 20.sp, maxLines = 2, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.padding(start = 60.dp))
                            Image(painter = painterResource(id = R.drawable.alarm), contentDescription = "", modifier = Modifier
                                .height(80.dp)
                                .width(80.dp))
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                            Button(
                                onClick = {
                                          navController.navigate(Screen.Timer.route)
                                },
                                modifier = Modifier
                                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20))
                                    .height(40.dp)
                                    .width(200.dp),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        20,
                                        184,
                                        56,
                                        255
                                    )
                                )

                            ) {
                                Text(
                                    text = "Set Timer",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.padding(top = 30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Card(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10),
                        contentColor = Color.Black,
                        backgroundColor = Color(108, 146, 191, 255),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .drawWithContent {
                                val paddingPx = with(density) { padding.toPx() }
                                clipRect(
                                    left = -paddingPx,
                                    top = 0f,
                                    right = size.width + paddingPx,
                                    bottom = size.height + paddingPx
                                ) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(
                                        text = "Create Your",
                                        fontSize = 20.sp,
                                        maxLines = 2,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Schedule",
                                        fontSize = 20.sp,
                                        maxLines = 2,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "for better",
                                        fontSize = 20.sp,
                                        maxLines = 2,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "results.",
                                        fontSize = 20.sp,
                                        maxLines = 2,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.bookmark),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .height(90.dp)
                                        .width(80.dp)
                                )
                            }
                            Spacer(modifier = Modifier.padding(top = 20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Button(
                                    onClick = {
                                              navController.navigate(Screen.Note.route)
                                    },
                                    modifier = Modifier
                                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20))
                                        .height(40.dp)
                                        .width(100.dp),
                                    shape = RoundedCornerShape(20),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(
                                            239, 243, 248, 255
                                        )
                                    )

                                ) {
                                    Text(
                                        text = "Create",
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))

                    Card(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10),
                        contentColor = Color.Black,
                        backgroundColor = Color(190,190,253,255),
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxWidth(0.9f)
                            .drawWithContent {
                                val paddingPx = with(density) { padding.toPx() }
                                clipRect(
                                    left = -paddingPx,
                                    top = 0f,
                                    right = size.width + paddingPx,
                                    bottom = size.height + paddingPx
                                ) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Spacer(modifier = Modifier.padding(start = 60.dp))
                                Image(painter = painterResource(id = R.drawable.stats), contentDescription = "", modifier = Modifier
                                    .height(80.dp)
                                    .fillMaxWidth(0.8f)
                                )
                                Spacer(modifier = Modifier.padding(top = 10.dp))
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                                Button(
                                    onClick = {
                                              navController.navigate(Screen.Statistics.route)
                                    },
                                    modifier = Modifier
                                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20))
                                        .height(40.dp)
                                        .width(200.dp),
                                    shape = RoundedCornerShape(20),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(
                                            145,134,250,255
                                        )
                                    )

                                ) {
                                    Text(
                                        text = "Detailed Stats",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                            }

                        }
                    }
                }
            }
        }
    }
}

