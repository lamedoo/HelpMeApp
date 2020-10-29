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

data class UserProfile(
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("last_name")
        val lastName: String
    )
}