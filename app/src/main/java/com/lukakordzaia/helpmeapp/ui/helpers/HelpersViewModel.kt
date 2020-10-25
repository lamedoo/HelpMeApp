package com.lukakordzaia.helpmeapp.ui.helpers

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelpersRepository

class HelpersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HelpersRepository(application)
    val showProgress : LiveData<Boolean>
    val helpersList : LiveData<List<Helpers>>


    init {
        this.showProgress = repository.showProgress
        this.helpersList = repository.helpersList
    }

    fun getAllHelpers() {
            repository.getAllHelpers()
    }
}