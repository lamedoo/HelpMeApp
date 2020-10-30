package com.lukakordzaia.helpmeapp.network.model


import com.google.gson.annotations.SerializedName

data class Helpers(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("city")
    val city: String,
    @SerializedName("address")
    val address: String
)