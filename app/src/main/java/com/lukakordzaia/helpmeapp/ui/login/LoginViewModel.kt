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

class LoginViewModel() : ViewModel() {
    private val repository = AuthRepository()
    private val _loginSuccess = MutableLiveData<Boolean>()
    private val _loginError = MutableLiveData<String>()

    var loginSuccess: LiveData<Boolean> = _loginSuccess
    var loginError: LiveData<String> = _loginError

    fun userLogin(auth: FirebaseAuth, email: String, password: String) {
        viewModelScope.launch {
            try {
                repository.authenticate(auth, email, password)?.let {
                    _loginSuccess.value = true
                    val user = it.uid
                    AppPreferences.user_token = user
                } ?: run {
                    _loginSuccess.value = false
                    _loginError.value = "სცადეთ თავიდან"
                }
            } catch (e: FirebaseAuthException) {
                _loginSuccess.value = false
                _loginError.value = "სცადეთ თავიდან"
            }
        }
    }
}