package com.lukakordzaia.helpmeapp.repository

import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.UserProfile

class UserProfileRepository {
    private val destinationService = ServiceBuilder.buildService(HelpersNetwork::class.java)

    suspend fun getUserProfile(userId: Int) : Result<UserProfile> {
        val response = destinationService.getSingleUser(userId)
        return if (response.isSuccessful) {
            Result.Success(response.body()!!)
        } else {
            Result.Error(response.errorBody()?.string() ?: "Something goes wrong")
        }

    }
}