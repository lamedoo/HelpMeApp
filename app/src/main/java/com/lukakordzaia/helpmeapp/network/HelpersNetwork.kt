package com.lukakordzaia.helpmeapp.network

import com.lukakordzaia.helpmeapp.network.model.Helpers
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface HelpersNetwork {

    @GET ("users")
    suspend fun getHelpers() : Response<List<Helpers>>
}