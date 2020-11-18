package com.lukakordzaia.helpmeapp.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import com.lukakordzaia.helpmeapp.network.model.Address
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList

class UserAddressRepository {
    suspend fun addUserAddress(userId: String, address: String): Boolean {
        return try {
            val id = UUID.randomUUID().toString()
            Firebase.firestore
                .collection("users")
                .document(userId)
                .collection("addresses")
                .document(id)
                .set(mapOf(
                    "id" to id,
                    "address" to address,
                    "details" to null
                ))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addAddressDetails(userId: String, addressId: String, addressDetails: String): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userId)
                .collection("addresses")
                .document(addressId)
                .update(mapOf(
                    "details" to addressDetails
                ))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteUserAddress(userId: String, addressId: String): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userId)
                .collection("addresses")
                .document(addressId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }



    suspend fun getSingleAddress(userId: String, addressId: String) : DocumentSnapshot? {
        return try {
            val data = Firebase.firestore
                .collection("users")
                .document(userId)
                .collection("addresses")
                .document(addressId)
                .get()
                .await()

            data
        } catch (e: Exception) {
            null
        }
    }

    fun getUserAddress(userId: String, firestoreAddressesCallBack: FirestoreAddressesCallBack) {
        val docRef = Firebase.firestore.collection("users").document(userId).collection("addresses")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null ) {
                val addresses: MutableList<Address> = ArrayList()
                for (address in snapshot) {
                    addresses.add(Address(address.data["id"].toString(), address.data["address"].toString(), address.data["details"].toString()))
                }
                firestoreAddressesCallBack.onCallback(addresses)
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        }
    }
}