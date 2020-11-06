package com.lukakordzaia.helpmeapp.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    val avatar: String? = "",
    val email: String? = "",
    val name: String? = "",
    val lastName: String? = "",
    val phone: String? = ""
)

data class UserUpdate(
    val email: String? = "",
    val name: String? = "",
    val lastName: String? = "",
    val phone: String? = ""
)