package com.bits.hackathon.studyfocus.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.bits.hackathon.studyfocus.R
import com.bits.hackathon.studyfocus.Screen
import com.bits.hackathon.studyfocus.viewmodels.LoginViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController, loginViewModel: LoginViewModel
) {
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var dialog by remember {
        mutableStateOf(false)
    }
    var user by remember { mutableStateOf(mAuth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = { result ->
        user = result.user
        loginViewModel.setInitialDataToFirestore(navController.context)
        navController.navigate(route = Screen.MainScreen.route)
    }, onAuthError = {
        dialog = false
        user = null
    })
    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(token)
        .requestEmail().build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.secondary),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 160.dp))
                Image(painter = painterResource(id = R.drawable.timerlogo), contentDescription = "", modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight(0.3f))
                Text(
                    "InFonic",
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(top = 50.dp))
                Spacer(Modifier.padding(vertical = 30.dp))
                Button(
                    modifier = Modifier.shadow(elevation = 8.dp, shape = RoundedCornerShape(20)).height(60.dp).width(300.dp),
                    onClick = {
                        dialog = true
                    launcher.launch(googleSignInClient.signInIntent)
                    },
                    shape = RoundedCornerShape(20), // = 50% percent
                    // or shape = CircleShape
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                ){
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google_logo),
                                modifier = Modifier.size(20.dp),
                                contentDescription = "Google Icon",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sign in With Google",
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                when (dialog) {
                    true -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colors.onSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit, onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = FirebaseAuth.getInstance().signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}
