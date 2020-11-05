package com.lukakordzaia.helpmeapp.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lukakordzaia.helpmeapp.network.FirebaseResult
import com.lukakordzaia.helpmeapp.network.model.User

class UserProfileRepository {

//    fun getUserData() {
//        val dbReference = FirebaseDatabase.getInstance().getReference("Users")
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        val uid = currentUser!!.uid
//
//        dbReference.child(uid).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) : User {
//                val user = dataSnapshot.getValue(User::class.java)
//                return user!!
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e(ContentValues.TAG, "Failed to read user", error.toException())
//            }
//        })
//    }
}