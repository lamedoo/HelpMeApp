package com.lukakordzaia.helpmeapp.network.model

import com.google.gson.annotations.SerializedName

data class Address (
    @SerializedName("address")
    val address: String,
    @SerializedName("details")
    val details: String?
)