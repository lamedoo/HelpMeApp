package com.lukakordzaia.helpmeapp.ui.userprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserProfileEditViewModel : ViewModel() {
    private val _editName = MutableLiveData<String>()
    private val _editLastName = MutableLiveData<String>()
    private val _editEmail = MutableLiveData<String>()
    private val _editNumber = MutableLiveData<String>()

    val editName: LiveData<String> = _editName
    val editLastName: LiveData<String> = _editLastName
    val editEmail: LiveData<String> = _editEmail
    val editNumber: LiveData<String> = _editNumber

}