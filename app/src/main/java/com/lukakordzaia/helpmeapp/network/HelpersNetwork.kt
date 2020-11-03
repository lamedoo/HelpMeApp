package com.lukakordzaia.helpmeapp.network

import com.lukakordzaia.helpmeapp.network.model.Helpers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HelpersNetwork {

    @GET ("https://5f99df9d50d84900163b9290.mockapi.io/users")
    suspend fun getHelpers() : Response<List<Helpers>>

    @GET ("https://5f99df9d50d84900163b9290.mockapi.io/users/{id}")
    suspend fun getSingleHelper(@Path ("id") id: Int) : Response<Helpers>
}