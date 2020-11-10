package com.lukakordzaia.helpmeapp.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import com.lukakordzaia.helpmeapp.network.model.UserUpdate
import com.lukakordzaia.helpmeapp.network.room.HelpMeAppDatabase
import kotlinx.coroutines.tasks.await
import java.util.*

class UserProfileEditRepository {

    fun getUserEditData(userName: String, firebaseCallback: FirebaseCallBack) {
        val docRef = Firebase.firestore.collection("users").document(userName)
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


    suspend fun updateUserData(userName: String, userData: UserUpdate): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userName)
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

    suspend fun updateUserDataToRoom(context: Context, userName: String, userData: UserUpdate) {
        HelpMeAppDatabase.getDatabase(context)?.getDao()?.updateUserData(
            userData.name!!,
            userData.lastName!!,
            userData.email!!,
            userData.phone!!,
            userName
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

    suspend fun uploadUserAvatarToRoom(context: Context, filepath: String, userName: String) {
        HelpMeAppDatabase.getDatabase(context)?.getDao()?.updateUserAvatar(filepath, userName)
    }


    suspend fun saveUserAvatarToFirestore(userName: String, userAvatar: String): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userName)
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

    suspend fun saveUserAddress(userName: String, address: String): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userName)
                .collection("addresses")
                .document(address)
                .set(mapOf(
                    "address" to address
                ))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteUserAddress(userName: String, address: String): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userName)
                .collection("addresses")
                .document(address)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUserAddress(userName: String, firestoreAddressesCallBack: FirestoreAddressesCallBack) {
        val docRef = Firebase.firestore.collection("users").document(userName).collection("addresses")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null ) {
                val addresses = ArrayList<String>()
                for (address in snapshot) {
                    addresses.add(address.data["address"].toString())
                }
                firestoreAddressesCallBack.onCallback(addresses)
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }
    }
}