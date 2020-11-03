package com.lukakordzaia.helpmeapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.lukakordzaia.helpmeapp.repository.AuthRepository
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepository()
    private val _registerSuccess = MutableLiveData<Boolean>()

    val registerSuccess: LiveData<Boolean> = _registerSuccess

    fun userLogin(auth: FirebaseAuth, email: String, password: String, name: String, lastName: String, number: String) {
        viewModelScope.launch {
            try {
                repository.register(auth, email, password, name, lastName, number)?.let {
                    _registerSuccess.value = true
                } ?: run {
                    _registerSuccess.value = false
                }
            } catch (e: FirebaseAuthException) {
                _registerSuccess.value = false
            }
        }
    }
}