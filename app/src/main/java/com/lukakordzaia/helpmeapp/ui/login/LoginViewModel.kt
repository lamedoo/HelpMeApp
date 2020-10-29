package com.lukakordzaia.helpmeapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.PostUser
import com.lukakordzaia.helpmeapp.repository.LoginRepository
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val repository = LoginRepository()
    private val _loginSuccess = MutableLiveData<Boolean>()
    private val _loginError = MutableLiveData<String>()

    var loginSuccess: LiveData<Boolean> = _loginSuccess
    var loginError: LiveData<String> = _loginError

    fun postUserLogin(user: PostUser) {
        viewModelScope.launch {
            when (val retrofit = repository.postUserLogin(user)) {
                is Result.Success -> {
                    _loginSuccess.value = true
                    AppPreferences.user_token = retrofit.data.token
                }
                is Result.Error -> {
                    _loginSuccess.value = false
                    _loginError.value = "სცადეთ თავიდან"
                    Log.d("login", "error")
                }
        }
        }

    }
}