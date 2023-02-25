package com.bits.hackathon.studyfocus.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bits.hackathon.studyfocus.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import org.checkerframework.checker.units.qual.m

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    var mAuth = FirebaseAuth.getInstance()
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.secondary
    ) {
        Scaffold(
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome, ${mAuth.currentUser?.displayName}")
                Spacer(modifier = Modifier.padding(20.dp))
                Card(
                    elevation = 10.dp,
                    contentColor = Color.Blue,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column() {
                        Row() {
                            Text(text = "Hi")
                            Spacer(modifier = Modifier.padding(start = 10.dp))

                        }

                    }
                }
            }
        }
    }
}