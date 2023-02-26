package com.bits.hackathon.studyfocus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bits.hackathon.studyfocus.navigation.SetupNavGraph
import com.bits.hackathon.studyfocus.ui.theme.StudyFocusTheme
import com.bits.hackathon.studyfocus.viewmodelfactories.*
import com.bits.hackathon.studyfocus.viewmodels.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val mainViewModel: MainViewModel by viewModels {
            MainViewModelFactory()
        }
        val loginViewModel: LoginViewModel by viewModels {
            LoginViewModelFactory(db,mAuth)
        }
        val timerViewModel: TimerViewModel by viewModels {
            TimerViewModelFactory()
        }
        val rewardsViewModel: RewardsViewModel by viewModels {
            RewardsViewModelFactory(db, mAuth)
        }
        val statisticsViewModel: StatisticsViewModel by viewModels {
            StatisticsViewModelFactory(db, mAuth)
        }
        val allSessionsViewModel: AllSessionsViewModel by viewModels {
            AllSessionsViewModelFactory(db,mAuth)
        }
        Log.d("ujj","j")
        setContent {

            StudyFocusTheme {

                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController, mainViewModel = mainViewModel, loginViewModel = loginViewModel, timerViewModel = timerViewModel, rewardsViewModel =rewardsViewModel
                , statisticsViewModel = statisticsViewModel, allSessionsViewModel =allSessionsViewModel
                )

                if (mAuth.currentUser == null) {
                    navController.navigate(route = Screen.Login.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}


