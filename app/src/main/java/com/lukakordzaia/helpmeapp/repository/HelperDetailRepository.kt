package com.lukakordzaia.helpmeapp.repository

import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.network.Result

class HelperDetailRepository {
    private val destinationService = ServiceBuilder.buildService(HelpersNetwork::class.java)

    suspend fun getSingleHelper(helperId: Int) : Result<Helpers> {
        return try {
            val response = destinationService.getSingleHelper(helperId)
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