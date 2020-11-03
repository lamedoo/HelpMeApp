package com.lukakordzaia.helpmeapp.ui.userprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.repository.UserProfileRepository
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class UserProfileViewModel: ViewModel() {
    private val repository = UserProfileRepository()
    private val  _userFullName = MutableLiveData<String>()
    private val _userAvatar = MutableLiveData<String>()

    val userFullName: LiveData<String> = _userFullName
    val userAvatar: LiveData<String> = _userAvatar

    init {
        getUserProfile(4)
    }

    fun removeSaveToken() {
        AppPreferences.user_token = ""
        Firebase.auth.signOut()
    }

    fun getUserProfile(userId: Int) {
        viewModelScope.launch {
            when (val retrofit = repository.getUserProfile(userId)) {
                is Result.Success -> {
                    val data = retrofit.data.data
                    if (AppPreferences.user_token.isNotEmpty()) {
                        _userFullName.value = "${data.firstName} ${data.lastName}"
                        _userAvatar.value = data.avatar
                    }
                }
                is Result.Error -> {
                    Log.d("userProfile", "error")
                }
            }
        }
    }
}