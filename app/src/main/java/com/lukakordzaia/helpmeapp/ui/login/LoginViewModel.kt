package com.lukakordzaia.helpmeapp.ui.login

import android.content.ContentValues.TAG
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.PostUser
import com.lukakordzaia.helpmeapp.repository.LoginRepository
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.android.synthetic.main.fragment_register.*
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

    fun userLogin(auth: FirebaseAuth, email: String, password: String) {
        viewModelScope.launch {
            try {
                repository.authenticate(auth, email, password)?.let {
                    _loginSuccess.value = true
                    val user = auth.currentUser?.uid
                    AppPreferences.user_token = user!!
                } ?: run {
                    _loginSuccess.value = false
                    _loginError.value = "სცადეთ თავიდან"
                }
            } catch (e: FirebaseAuthException) {
            }
        }
    }


    fun userLogin1(auth: FirebaseAuth, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    _loginSuccess.value = true
                    val user = auth.currentUser?.uid
                    AppPreferences.user_token = user!!
                } else {
                    _loginSuccess.value = false
                    _loginError.value = "სცადეთ თავიდან"
                }
            }
    }
}