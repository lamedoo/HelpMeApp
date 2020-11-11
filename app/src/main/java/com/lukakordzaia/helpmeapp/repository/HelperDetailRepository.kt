package com.lukakordzaia.helpmeapp.repository

import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.Helpers

class HelperDetailRepository {
    private val destinationService = ServiceBuilder.buildService(HelpersNetwork::class.java)

    suspend fun getSingleHelper(helperId: Int): Result<Helpers> {
        return ServiceBuilder.retrofitCall { destinationService.getSingleHelper(helperId) }
    }
}