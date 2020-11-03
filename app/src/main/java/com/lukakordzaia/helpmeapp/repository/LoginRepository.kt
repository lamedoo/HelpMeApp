package com.lukakordzaia.helpmeapp.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.network.model.PostUser
import com.lukakordzaia.helpmeapp.network.model.UserToken
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.tasks.await

class LoginRepository {
    private val destinationService = ServiceBuilder.buildService(HelpersNetwork::class.java)

    suspend fun postUserLogin(user: PostUser): Result<UserToken> {
        return try {
            val response = destinationService.postUserLogin(user)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(response.errorBody()?.string() ?: "Something goes wrong")
            }

        }
        catch (e: Exception) {
            Result.Error(e.message ?: "Internet error runs")
        }
    }



    suspend fun authenticate(auth: FirebaseAuth, email: String, password: String): FirebaseUser? {
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser ?: throw FirebaseAuthException("", "")
    }
}