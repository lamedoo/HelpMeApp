package com.lukakordzaia.helpmeapp.repository


import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.Helpers


class HelpersRepository {
    private val destinationService = ServiceBuilder.buildService(HelpersNetwork::class.java)

    suspend fun getAllHelpers(): Result<List<Helpers>> {
        return try {
            val response = destinationService.getHelpers()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(response.errorBody()?.string() ?: "Something goes wrong")
            }

        } catch (e: Exception) {
            Result.Error(e.message ?: "Internet error runs")
        }
    }
}