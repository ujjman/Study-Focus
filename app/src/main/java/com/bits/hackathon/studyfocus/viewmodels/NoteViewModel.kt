package com.bits.hackathon.studyfocus.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NoteViewModel: ViewModel() {
    var showOverlayDialog by mutableStateOf(false)
    var forGranted by mutableStateOf(false)
}