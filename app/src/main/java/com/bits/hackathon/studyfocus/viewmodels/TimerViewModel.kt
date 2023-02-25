package com.bits.hackathon.studyfocus.viewmodels

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel: ViewModel() {

    var hour = MutableLiveData(0)
    var minute = MutableLiveData(0)
    var second = MutableLiveData(0)
    var showGrantOverlayDialog by mutableStateOf(false)
    fun setTime(hr: Int, min: Int, sec: Int)
    {
        val totSec = (hr * 3600) + (min * 60) + (sec)
        hour.value =hr
        minute.value=min
        second.value=sec
        time.value = totSec
    }
    fun checkHr(hr: Int): Boolean {
        return hr <= 99
    }

    fun checkMin(min: Int): Boolean {
        return min <= 59
    }

    fun checkSec(sec: Int): Boolean {
        return sec <= 59
    }

    private var countDownTimer: CountDownTimer? = null


    val time = MutableLiveData(0)
}