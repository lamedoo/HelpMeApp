package com.lukakordzaia.helpmeapp.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.model.UserUpdate
import com.lukakordzaia.helpmeapp.network.room.HelpMeAppDatabase
import kotlinx.coroutines.tasks.await
import java.util.*

class UserProfileEditRepository {

    fun getUserEditData(userId: String, firebaseCallback: FirebaseCallBack) {
        val docRef = Firebase.firestore.collection("users").document(userId)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let {
                    firebaseCallback.onCallback(it)
                }
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }
    }


    suspend fun updateUserData(userId: String, userData: UserUpdate): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userId)
                .update(
                    mapOf(
                        "name" to userData.name,
                        "lastName" to userData.lastName,
                        "email" to userData.email,
                        "phone" to userData.phone
                    )
                )
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUserDataToRoom(context: Context, userId: String, userData: UserUpdate) {
        HelpMeAppDatabase.getDatabase(context)?.getDao()?.updateUserData(
            userData.name!!,
            userData.lastName!!,
            userData.email!!,
            userData.phone!!,
            userId
        )
    }

    suspend fun uploadUserAvatar(filepath: Uri): String {
        return try {
            val storageReference =
                Firebase.storage.reference.child("userAvatars/" + UUID.randomUUID().toString())
            val data = storageReference
                .putFile(filepath)
                .await()
                .storage
                .downloadUrl
                .await()

            data.toString()
        } catch (e: Exception) {
            e.message.toString()
        }
    }

    suspend fun uploadUserAvatarToRoom(context: Context, filepath: String, userId: String) {
        HelpMeAppDatabase.getDatabase(context)?.getDao()?.updateUserAvatar(filepath, userId)
    }


    suspend fun saveUserAvatarToFirestore(userId: String, userAvatar: String): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userId)
                .update(
                    mapOf(
                        "avatar" to userAvatar
                    )
                )
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}