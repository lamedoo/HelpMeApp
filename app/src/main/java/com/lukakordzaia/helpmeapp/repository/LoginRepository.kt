package com.lukakordzaia.helpmeapp.repository

import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.network.model.PostUser
import com.lukakordzaia.helpmeapp.network.model.UserToken

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
}