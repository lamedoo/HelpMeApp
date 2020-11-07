package com.lukakordzaia.helpmeapp.network.model

data class UserRegister(
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