package com.lukakordzaia.helpmeapp.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import kotlinx.coroutines.tasks.await


class UserProfileRepository {

    fun getUserData(userName: String, firebaseCallback: FirebaseCallBack) {
        val docRef = Firebase.firestore.collection("users").document(userName)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let { firebaseCallback.onCallback(it) }
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }
    }
}