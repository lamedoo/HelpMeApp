package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.repository.HelperDetailRepository
import com.lukakordzaia.helpmeapp.network.Result
import kotlinx.coroutines.launch

class HelperDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HelperDetailRepository()
    private val _helperName = MutableLiveData<String>()
    private val _showProgress = MutableLiveData<Boolean>()

    val helperName : LiveData<String> = _helperName
    val showProgress : LiveData<Boolean> = _showProgress


    fun getSingleHelper(helperId: Int) {
        viewModelScope.launch {
            when (val retrofit = repository.getSingleHelper(helperId)) {
                is Result.Success -> {
                    _showProgress.value = false
                    _helperName.value = retrofit.data.name
                }
                is Result.Error -> {
                    _showProgress.value = false
                    Log.d("error", "error")
                }
            }
        }
    }
}