package com.lukakordzaia.helpmeapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lukakordzaia.helpmeapp.network.model.User
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private lateinit var dbReference: DatabaseReference

    suspend fun authenticate(auth: FirebaseAuth, email: String, password: String): FirebaseUser? {
        auth.signInWithEmailAndPassword(email, password).await()
        if (auth.currentUser != null) {
            return auth.currentUser
        } else {
            throw FirebaseAuthException("", "")
        }
    }

    suspend fun register(
        auth: FirebaseAuth,
        email: String,
        password: String,
        name: String,
        lastName: String,
        number: String
    ) : FirebaseUser? {
        auth.createUserWithEmailAndPassword(email, password).await()
        if (auth.currentUser != null) {
            var uid = auth.currentUser!!.uid
            dbReference = FirebaseDatabase.getInstance().getReference("Users")
            val user = User(email, name, lastName, number)

            dbReference.child(uid).setValue(user)

            return auth.currentUser
        } else {
            throw FirebaseAuthException("", "")
        }
    }
}