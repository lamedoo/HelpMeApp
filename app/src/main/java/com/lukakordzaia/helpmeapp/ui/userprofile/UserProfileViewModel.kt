package com.lukakordzaia.helpmeapp.ui.userprofile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.model.User
import com.lukakordzaia.helpmeapp.repository.UserProfileRepository
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class UserProfileViewModel: ViewModel() {
    private val repository = UserProfileRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _showContent = MutableLiveData<Boolean>()
    private val _userDataList = MutableLiveData<User>()

    val showProgress: LiveData<Boolean> = _showProgress
    val showContent: LiveData<Boolean> = _showContent
    val userDataList: LiveData<User> = _userDataList

    init {
        _showContent.value = false
        addUserChangeListener()
    }

    fun removeSaveToken() {
        AppPreferences.user_token = ""
        Firebase.auth.signOut()
    }

    private fun addUserChangeListener(){
        val currentUser = Firebase.auth.currentUser?.uid

        repository.getUserData(currentUser!!, object: FirebaseCallBack {
            override fun onCallback(userData: MutableMap<String, Any>) {
                _showProgress.value = false
                _showContent.value = true
                _userDataList.value = User(
                    "${userData.get("avatar")}",
                    "${userData.get("email")}",
                    "${userData.get("name")}",
                    " ${userData.get("lastName")}",
                    "${userData.get("phone")}"
                )
            }
        })
    }
}