package com.lukakordzaia.helpmeapp.network

import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.network.model.PostUser
import com.lukakordzaia.helpmeapp.network.model.UserProfile
import com.lukakordzaia.helpmeapp.network.model.UserToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HelpersNetwork {

    @GET ("users")
    suspend fun getHelpers() : Response<List<Helpers>>

    @GET ("users/{id}")
    suspend fun getSingleHelper(@Path ("id") id: Int) : Response<Helpers>

    @POST("https://reqres.in/api/login")
    suspend fun postUserLogin(@Body userLogin: PostUser) : Response<UserToken>

    @GET("https://reqres.in/api/users/{id}")
    suspend fun getSingleUser(@Path ("id") id: Int) : Response<UserProfile>
}