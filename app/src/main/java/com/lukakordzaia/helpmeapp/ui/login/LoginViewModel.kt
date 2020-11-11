package com.lukakordzaia.helpmeapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.repository.AuthRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {
    private val repository = AuthRepository()
    private val _loginSuccess = MutableLiveData<Boolean>()
    private val _loginError = MutableLiveData<String>()

    var loginSuccess: LiveData<Boolean> = _loginSuccess
    var loginError: LiveData<String> = _loginError

    fun onTokenExists() {
        if (AppPreferences.user_token.isNotEmpty()) {
            navigateToNewFragment(R.id.action_loginFragment_to_homeFragment)
        }
    }

    fun onRegisterPressed() {
        navigateToNewFragment(R.id.action_loginFragment_to_registerFragment)
    }

    fun userLogin(auth: FirebaseAuth, email: String, password: String) {
        viewModelScope.launch {
            val result = repository.authenticate(auth, email, password)
            if ( result != null) {
                _loginSuccess.value = true
                navigateToNewFragment(R.id.action_loginFragment_to_homeFragment)
                val user = result.user!!.uid
                AppPreferences.user_token = user
            } else {
                _loginSuccess.value = false
                _loginError.value = "სცადეთ თავიდან"
            }
        }
    }
}