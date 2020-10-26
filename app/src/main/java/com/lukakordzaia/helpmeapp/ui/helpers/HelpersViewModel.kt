package com.lukakordzaia.helpmeapp.ui.helpers

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelpersRepository

class HelpersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HelpersRepository(application)
    val showProgress = MutableLiveData<Boolean>()
    var helpersList : LiveData<List<Helpers>>


    init {
        this.helpersList = repository.helpersList
    }

    fun getAllHelpers() {
        if (helpersList.value != null) {
            showProgress.value = false
        }
        repository.getAllHelpers()
    }
}