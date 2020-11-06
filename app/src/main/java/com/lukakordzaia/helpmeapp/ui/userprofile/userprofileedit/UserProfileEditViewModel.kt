package com.lukakordzaia.helpmeapp.ui.userprofile.userprofileedit

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.model.User
import com.lukakordzaia.helpmeapp.network.model.UserUpdate
import com.lukakordzaia.helpmeapp.repository.UserProfileRepository
import kotlinx.coroutines.launch

class UserProfileEditViewModel : ViewModel() {
    private val repository = UserProfileRepository()
    private val _userDataList = MutableLiveData<User>()

    val userDataList: LiveData<User> = _userDataList

    init {
        addUserChangeListener()
    }


    private fun addUserChangeListener(){
        val currentUser = Firebase.auth.currentUser?.uid

        repository.getUserData(currentUser!!, object: FirebaseCallBack {
            override fun onCallback(userData: MutableMap<String, Any>) {
                _userDataList.value = User(
                    "${userData["avatar"]}",
                    "${userData["email"]}",
                    "${userData["name"]}",
                    "${userData["lastName"]}",
                    "${userData["phone"]}"
                )
            }
        })
    }

    fun updateUserData(name: String, lastName: String, email: String, number: String, filePath: Uri?) {
        val currentUser = Firebase.auth.currentUser?.uid
        val userData = UserUpdate(email, name, lastName, number)
        viewModelScope.launch {
            val update = currentUser?.let { repository.updateUserData(it, userData) }
            if (update == true) {
                Log.d("dataUpdate", "success")
            }
        }

        if (filePath != null) {
            viewModelScope.launch {
                val uploadAvatar = repository.uploadUserAvatar(filePath)
                if (uploadAvatar.isNotEmpty()) {
                    repository.saveUserAvatarToDB(currentUser!!, uploadAvatar)
                }
            }
        }
    }
}