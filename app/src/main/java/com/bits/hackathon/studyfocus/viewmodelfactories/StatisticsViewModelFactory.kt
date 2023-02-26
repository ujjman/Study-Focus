package com.bits.hackathon.studyfocus.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bits.hackathon.studyfocus.viewmodels.StatisticsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StatisticsViewModelFactory(
    private val db: FirebaseFirestore, private val mAuth: FirebaseAuth
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatisticsViewModel::class.java)) {
            return StatisticsViewModel(db, mAuth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}