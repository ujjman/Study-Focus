package com.bits.hackathon.studyfocus


import com.bits.hackathon.studyfocus.data.SessionDetails
import com.bits.hackathon.studyfocus.data.Sessions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun addSession(sessionDetails: SessionDetails) {
    val db = FirebaseFirestore.getInstance()
    var list = ArrayList<SessionDetails>()
    list.add(sessionDetails)
    var sessions = Sessions(list)
    db.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid.toString())
        .collection("sessions").document(sessionDetails.date.toString()).get()
        .addOnSuccessListener { document ->
            try {
                if (document != null) {
                    sessions = document.toObject(Sessions::class.java)!!
                    if (sessions.sessions == null) {
                        sessions.sessions = list
                    } else {
                        sessions.sessions!!.add(sessionDetails)
                    }
                    db.collection("users")
                        .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                        .collection("sessions").document(sessionDetails.date.toString())
                        .set(sessions)
                }
            } catch (e: Exception) {
                db.collection("users")
                    .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                    .collection("sessions").document(sessionDetails.date.toString()).set(sessions)
            }
        }
}

