package com.lukakordzaia.helpmeapp.network.model

import com.google.gson.annotations.SerializedName

data class Address (
    @SerializedName("id")
    val id: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("details")
    val details: String?
)