package com.lukakordzaia.helpmeapp.network

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
}

sealed class FirebaseResult<out T: Any> {
    data class Success<out T : Any>(val data: T) : FirebaseResult<T>()
    data class Error(val exception: String) : FirebaseResult<Nothing>()
}