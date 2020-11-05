package com.lukakordzaia.helpmeapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
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
            val result = repository.authenticate(auth, email, password)
            if ( result != null) {
                _loginSuccess.value = true
                val user = result.user!!.uid
                AppPreferences.user_token = user
            } else {
                _loginSuccess.value = false
                _loginError.value = "სცადეთ თავიდან"
            }
        }
    }
}