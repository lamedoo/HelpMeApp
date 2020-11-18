package com.lukakordzaia.helpmeapp.repository

class OrderChooseDetailsRepository {

//    fun getUserAddress(userName: String, firestoreAddressesCallBack: FirestoreAddressesCallBack) {
//        val docRef = Firebase.firestore.collection("users").document(userName).collection("addresses")
//        docRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(ContentValues.TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//            if (snapshot != null ) {
//                val addresses = ArrayList<String>()
//                for (address in snapshot) {
//                    addresses.add(address.data["address"].toString())
//                }
//                firestoreAddressesCallBack.onCallback(addresses)
//            } else {
//                Log.d(ContentValues.TAG, "Current data: null")
//            }
//        }
//    }
}