package com.bits.hackathon.studyfocus.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bits.hackathon.studyfocus.viewmodels.LoginViewModel
import com.bits.hackathon.studyfocus.viewmodels.NoteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NoteViewModelFactory(
    private val db: FirebaseFirestore,
    private val mAuth: FirebaseAuth
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}