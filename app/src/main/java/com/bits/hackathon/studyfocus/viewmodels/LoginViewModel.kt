package com.bits.hackathon.studyfocus.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bits.hackathon.studyfocus.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.bits.hackathon.studyfocus.data.UserData

class LoginViewModel(
    private val db: FirebaseFirestore, private val mAuth: FirebaseAuth
) : ViewModel() {

    fun setInitialDataToFirestore(context: Context) {
        var userData =
            UserData(mAuth.currentUser?.displayName, null)
        db.collection("users").document(mAuth.currentUser?.uid.toString()).get()
            .addOnSuccessListener { document ->
                try {
                    if (document != null) {
                        userData = document.toObject(UserData::class.java)!!
                        if (userData.username.equals("", true)) {
                            userData = UserData(
                                mAuth.currentUser?.displayName,
                                null
                            )
                        }
                        db.collection("users").document(mAuth.currentUser?.uid.toString())
                            .set(userData)
                    }

                } catch (e: Exception) {
                    db.collection("users").document(mAuth.currentUser?.uid.toString()).set(userData)
                }
            }.addOnFailureListener {

            }

    }

}
