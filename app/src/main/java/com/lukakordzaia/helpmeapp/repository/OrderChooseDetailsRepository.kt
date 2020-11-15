package com.lukakordzaia.helpmeapp.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import java.util.*

class OrderChooseDetailsRepository {

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