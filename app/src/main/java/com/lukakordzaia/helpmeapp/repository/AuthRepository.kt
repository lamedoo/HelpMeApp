package com.lukakordzaia.helpmeapp.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.model.User
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private lateinit var dbReference: DatabaseReference

    suspend fun authenticate(auth: FirebaseAuth, email: String, password: String): AuthResult? {
        return try {
            val data = auth.signInWithEmailAndPassword(email,password).await()
            data
        } catch (e : Exception) {
            null
        }
    }

    suspend fun register(
        auth: FirebaseAuth,
        email: String,
        password: String,
        name: String,
        lastName: String,
        number: String) : AuthResult? {

        return try {
            val data = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = data.user?.uid
            val db = Firebase.firestore
            val user = User(avatar = null, email, name, lastName, number)

            db.collection("users").document(uid!!).set(user)

            data
        } catch (e : Exception) {
            null
        }
    }
}