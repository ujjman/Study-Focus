package com.bits.hackathon.studyfocus.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.bits.hackathon.studyfocus.R
import com.bits.hackathon.studyfocus.Screen
import com.bits.hackathon.studyfocus.data.RewardsList
import com.bits.hackathon.studyfocus.viewmodels.RewardsViewModel
import com.bits.hackathon.studyfocus.viewmodels.TimerViewModel
import kotlin.random.Random

@Composable
fun RewardsScreen(navController: NavHostController, rewardsViewModel: RewardsViewModel) {

    when(rewardsViewModel.showOverlayDialog)
    {
        true->
        {
            showReward(context = navController.context, rewardsViewModel = rewardsViewModel)
        }
    }

    var redirect by remember {
        mutableStateOf(0)
    }
    when(redirect)
    {
        1->
        {
            redirect=0
            redirectGames(context = navController.context)
            navController.navigate(Screen.MainScreen.route)
            {
                popUpTo(0)
            }
        }
        2->
        {
            redirect=0
            redirectSongs(context = navController.context)
            navController.navigate(Screen.MainScreen.route)
            {
                popUpTo(0)
            }
        }
        3->
        {
            redirect=0
            redirectExercise(context = navController.context)
            navController.navigate(Screen.MainScreen.route)
            {
                popUpTo(0)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.secondary),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val padding = 10.dp
                Spacer(modifier = Modifier.padding(top = 60.dp))
                Text(text = "Claim Your Rewards!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Card(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10),
                    contentColor = Color.Black,
                    backgroundColor = Color(158, 200, 252, 255),
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
                                Text(
                                    text = "You won reward as",
                                    fontSize = 20.sp,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "a result of",
                                    fontSize = 20.sp,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "your focused work!",
                                    fontSize = 20.sp,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.padding(start = 60.dp))
                            Image(
                                painter = painterResource(id = R.drawable.gift),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(80.dp)
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
                                          rewardsViewModel.showOverlayDialog=true
                                },
                                modifier = Modifier
                                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20))
                                    .height(40.dp)
                                    .width(200.dp),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        163, 171, 246, 255
                                    )
                                )

                            ) {
                                Text(
                                    text = "Claim it",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.padding(top = 90.dp))
                Text(text = "Relax Now!!", fontWeight = FontWeight.Bold, fontSize = 32.sp)
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
                            Column(modifier = Modifier.padding(start =20.dp)) {
                                Spacer(modifier = Modifier.padding(top = 30.dp))
                                Text(
                                    text = "Select the",
                                    fontSize = 25.sp,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "way you",
                                    fontSize = 25.sp,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "want to",
                                    fontSize = 25.sp,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "relax!!",
                                    fontSize = 25.sp,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.padding(top = 20.dp))
                            }
                            Spacer(modifier = Modifier.padding(start = 40.dp))
                            Column() {
                                Image(
                                    painter = painterResource(id = R.drawable.game),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .height(90.dp)
                                        .width(90.dp)
                                )
                                Row() {
                                    Spacer(modifier = Modifier.padding(start = 50.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.headphone),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .height(90.dp)
                                            .width(90.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Button(
                                onClick = {
                                          redirect=1
                                },
                                modifier = Modifier
                                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20))
                                    .height(40.dp)
                                    .width(90.dp),
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
                                    text = "Games",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                            Button(
                                onClick = {
                                          redirect=2
                                },
                                modifier = Modifier
                                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20))
                                    .height(40.dp)
                                    .width(90.dp),
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
                                    text = "Lo-Fi",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                            Button(
                                onClick = {
                                          redirect=3
                                },
                                modifier = Modifier
                                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(20))
                                    .height(40.dp)
                                    .width(100.dp),
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
                                    text = "Exercise",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.padding(top = 20.dp))
                    }
                }
            }

        }
    }
}

@Composable
fun redirectGames(context: Context)
{
    val myRandomValue = Random.nextInt(2,50)
    var link =""
    if(myRandomValue%4==0)
    {
        link="https://sudoku.com/"
    }
    else if(myRandomValue%4 == 1)
    {
        link="https://flappybird.io/"
    }
    else if(myRandomValue%4==2)
    {
        link="https://doodlejump.io/"
    }
    else
    {
        link="https://poki.com/en/g/dinosaur-game"
    }
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link)) }
    context.startActivity(intent)
}
@Composable
fun redirectSongs(context: Context)
{
    val myRandomValue = Random.nextInt(2,50)
    var link =""
    if(myRandomValue%4==0)
    {
        link="https://youtu.be/D9_4BAuHEBw"
    }
    else if(myRandomValue%4 == 1)
    {
        link="https://youtu.be/_6_akBtKZdE"
    }
    else if(myRandomValue%4==2)
    {
        link="https://youtu.be/Ax06Q8FWk20"
    }
    else
    {
        link="https://youtu.be/KaSFoOF6Yw0"
    }
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link)) }
    context.startActivity(intent)
}
@Composable
fun redirectExercise(context: Context)
{
    val myRandomValue = Random.nextInt(2,50)
    var link =""
    if(myRandomValue%4==0)
    {
        link="https://youtu.be/SvPKFsCiMsw"
    }
    else if(myRandomValue%4 == 1)
    {
        link="https://youtu.be/4pKly2JojMw"
    }
    else if(myRandomValue%4==2)
    {
        link="https://youtu.be/X3-gKPNyrTA"
    }
    else
    {
        link="https://youtu.be/hKsAjoj4UK4"
    }
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link)) }
    context.startActivity(intent)
}

@Composable
fun showReward(context: Context, rewardsViewModel: RewardsViewModel) {

    val myRandomValue =  Random.nextInt(2, 50)
    val discount = Random.nextInt(20,80)
    val rewardList =RewardsList()
    var coupon = ""
    var couponName=""
    if(myRandomValue%4==0)
    {
        coupon=rewardList.amazon.toString()
        couponName="Amazon"
    }
    else if(myRandomValue%4 == 1)
    {
        coupon=rewardList.coursera.toString()
        couponName="Coursera"
    }
    else if(myRandomValue%4==2)
    {
        coupon=rewardList.udemy.toString()
        couponName="Udemy"
    }
    else
    {
        coupon=rewardList.flipkart.toString()
        couponName="Flipkart"
    }
    AlertDialog(onDismissRequest = {
        rewardsViewModel.showOverlayDialog = false
    }, confirmButton = {
        Button(onClick = {
            rewardsViewModel.showOverlayDialog = false
        }) {
            Text("Ok")
        }
    }, title = {
        Text("Congrats for winning coupon")
    }, text = {
        Column(

        ) {
            Text(text = "Congrats for winning coupon for ${discount}% on $couponName")
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Text(text = "Coupon Code is : ${coupon}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

    })
}