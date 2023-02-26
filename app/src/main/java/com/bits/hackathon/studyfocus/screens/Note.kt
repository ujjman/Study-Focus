package com.bits.hackathon.studyfocus.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bits.hackathon.studyfocus.BottomNavigationBar
import com.bits.hackathon.studyfocus.ui.theme.LightGreen
import com.bits.hackathon.studyfocus.viewmodels.NoteViewModel
import org.checkerframework.checker.units.qual.min

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Note(navController: NavHostController, noteViewModel: NoteViewModel)
{
    val sharedPrefs =
        LocalContext.current.getSharedPreferences("STUDY_FOCUS_BITS", Context.MODE_PRIVATE)
    val allEntries: Map<String, *> = sharedPrefs.all
    val padding =10.dp
    var text by remember { mutableStateOf(TextFieldValue("")) }
    when(noteViewModel.showOverlayDialog)
    {
        true->
        {
            addNote(noteViewModel = noteViewModel)
        }
    }
    when(noteViewModel.forGranted)
    {
        true->
        {

        }
        false->
        {

        }
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.secondary,
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 50.dp))
                Text(text = "Your Tasks", fontWeight = FontWeight.Bold, fontSize = 42.sp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.78f)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(10.dp), state = rememberLazyListState()
                    ) {
                        items(allEntries.size) { item ->

                            Card(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10),
                                contentColor = Color.Black,
                                backgroundColor = Color(122,113,246,255),
                                modifier = Modifier
                                    .padding(padding)
                                    .fillMaxWidth()
                                    .height(80.dp)
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

                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(modifier = Modifier.padding(start = 5.dp))
                                    Text(text = "$item) ", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                                    Text(text = allEntries.keys.elementAt(item), fontWeight = FontWeight.Bold, fontSize = 18.sp,color = Color.White)
                                    Spacer(modifier = Modifier.padding(start=10.dp))
                                    Text(text = allEntries.get(allEntries.keys.elementAt(item)).toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold,color = Color.White)

                                    Button(onClick = {
                                        noteViewModel.forGranted =!noteViewModel.forGranted
                                        sharedPrefs.edit().remove(allEntries.keys.elementAt(item)).apply()
                                    }) {
                                        Text(text = "Delete")
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(top = 10.dp))


                        }

                    }
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Row() {
                    OutlinedButton(
                        onClick = {
                                  noteViewModel.showOverlayDialog=true
                        },
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            backgroundColor = Color(190, 190, 253, 255)
                        )
                    ) {
                        // Adding an Icon "Add" inside the Button
                        Text(text = "+", fontSize = 30.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun addNote(noteViewModel: NoteViewModel)
{
    var text by remember {
        mutableStateOf("")
    }
    var time by remember {
        mutableStateOf("")
    }
    val sharedPrefs =
        LocalContext.current.getSharedPreferences("STUDY_FOCUS_BITS", Context.MODE_PRIVATE)
    AlertDialog(onDismissRequest = {
        noteViewModel.showOverlayDialog = false
    }, confirmButton = {
        Button(onClick = {
            noteViewModel.showOverlayDialog = false
            sharedPrefs.edit().putString(text, time).apply()
        }) {
            Text("Save")
        }
    }, title = {
        Text("Add a task")
    }, text = {
        Column(

        ) {
            TextField(value = text, onValueChange = { newText ->
                text = newText
            },
                placeholder = { Text(text = "Enter task title")}

            )
            Spacer(modifier = Modifier.padding(top = 5.dp))
            TextField(value = time, onValueChange = { newText ->
                time = newText
            },
                placeholder = {Text(text = "hh:mm:ss")}
            
            )
            
        }

    })
}