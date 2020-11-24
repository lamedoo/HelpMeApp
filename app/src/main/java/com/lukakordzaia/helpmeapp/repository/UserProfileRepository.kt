package com.lukakordzaia.helpmeapp.repository

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.room.HelpMeAppDatabase
import com.lukakordzaia.helpmeapp.network.room.Users
import com.lukakordzaia.helpmeapp.network.room.UsersDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserProfileRepository {

    fun getUserDataFromRoom(usersDao: UsersDao, userId: String): LiveData<Users> {
        return usersDao.getCurrentUserData(userId)
    }

    fun getUserData(context: Context, userId: String, firebaseCallback: FirebaseCallBack) {

        val docRef = Firebase.firestore.collection("users").document(userId)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let {
                    firebaseCallback.onCallback(it)

                    val avatar = if (it["avatar"].toString().isNotEmpty()) {
                        it["avatar"].toString()
                    } else {
                        null
                    }

                    val userData = Users(
                        0,
                        userId,
                        it["name"].toString(),
                        it["lastName"].toString(),
                        it["email"].toString(),
                        it["phone"].toString(),
                        avatar
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        HelpMeAppDatabase.getDatabase(context)?.getDao()?.insertUserData(userData)
                    }

                }
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }
    }
}