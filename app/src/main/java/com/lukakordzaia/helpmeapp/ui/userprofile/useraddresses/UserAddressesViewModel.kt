package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserAddressesViewModel : ViewModel() {
    private val _addressExists = MutableLiveData<Boolean>()

    val addressExists: LiveData<Boolean> = _addressExists

    init {
        _addressExists.value = false
    }
}