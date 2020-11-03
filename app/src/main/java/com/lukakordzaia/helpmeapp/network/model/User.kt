package com.lukakordzaia.helpmeapp.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    val email: String? = "",
    val name: String? = "",
    val lastName: String? = "",
    val phone: String? = ""
)