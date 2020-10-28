package com.lukakordzaia.helpmeapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL ="https://jsonplaceholder.typicode.com/"

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(getInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun <T> buildService (serviceType :Class<T>):T{
        return retrofit.create(serviceType)
    }
}
