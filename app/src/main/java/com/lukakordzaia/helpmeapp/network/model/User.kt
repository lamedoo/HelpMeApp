package com.lukakordzaia.helpmeapp.network.model

import com.google.gson.annotations.SerializedName

data class PostUser (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

data class UserToken (
    @SerializedName("token")
    val token: String
)