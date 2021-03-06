package com.lukakordzaia.helpmeapp.network

import com.lukakordzaia.helpmeapp.network.model.Address

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
}

interface FirebaseCallBack {
    fun onCallback(userData: MutableMap<String, Any>)
}

interface FirestoreAddressesCallBack {
    fun onCallback(addresses: MutableList<Address>)
}