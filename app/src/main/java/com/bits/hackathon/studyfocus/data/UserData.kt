package com.bits.hackathon.studyfocus.data

import com.bits.hackathon.studyfocus.data.Sessions

data class UserData(
    var username: String? = "",
    var sessions: Sessions? = null,
)