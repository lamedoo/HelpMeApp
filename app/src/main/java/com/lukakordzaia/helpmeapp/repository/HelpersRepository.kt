package com.lukakordzaia.helpmeapp.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.lukakordzaia.helpmeapp.network.HelpersNetwork
import com.lukakordzaia.helpmeapp.network.ServiceBuilder
import com.lukakordzaia.helpmeapp.network.model.Helpers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HelpersRepository(val application: Application) {
    val showProgress = MutableLiveData<Boolean>()
    val helpersList = MutableLiveData<List<Helpers>>()

    fun getAllHelpers() {
        showProgress.value = true

        val destinationService  = ServiceBuilder.buildService(HelpersNetwork::class.java)
        val requestCall = destinationService.getHelpers()
            requestCall.enqueue(object : Callback<List<Helpers>>{
            override fun onResponse(call: Call<List<Helpers>>, response: Response<List<Helpers>>) {
                    helpersList.value = response.body()
                    Log.d("response", "${helpersList.value}")
                    showProgress.value = false

            }

            override fun onFailure(call: Call<List<Helpers>>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, "Error while fetching the data", Toast.LENGTH_SHORT).show()
            }

        })
    }
}