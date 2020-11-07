package com.lukakordzaia.helpmeapp.ui.userprofile.userprofileedit

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.model.UserRegister
import com.lukakordzaia.helpmeapp.network.model.UserUpdate
import com.lukakordzaia.helpmeapp.repository.UserProfileEditRepository
import kotlinx.coroutines.launch

class UserProfileEditViewModel : ViewModel() {
    private val repository = UserProfileEditRepository()
    private val _userDataList = MutableLiveData<UserRegister>()

    val userDataList: LiveData<UserRegister> = _userDataList

    init {
        getUserEditData()
    }


    private fun getUserEditData(){
        val currentUser = Firebase.auth.currentUser?.uid

        repository.getUserEditData(currentUser!!, object: FirebaseCallBack {
            override fun onCallback(userData: MutableMap<String, Any>) {
                _userDataList.value = UserRegister(
                    "${userData["avatar"]}",
                    "${userData["email"]}",
                    "${userData["name"]}",
                    "${userData["lastName"]}",
                    "${userData["phone"]}"
                )
            }
        })
    }

    fun updateUserData(context: Context, name: String, lastName: String, email: String, number: String, filePath: Uri?) {
        val currentUser = Firebase.auth.currentUser?.uid
        val userData = UserUpdate(email, name, lastName, number)
        viewModelScope.launch {
            val update = currentUser?.let { repository.updateUserData(it, userData) }
            if (update == true) {
                Log.d("dataUpdate", "success")
            }
        }
        viewModelScope.launch {
            if (currentUser != null) {
                repository.updateUserDataToRoom(context, currentUser, userData)
            }
        }

        if (filePath != null) {
            viewModelScope.launch {
                val uploadAvatar = repository.uploadUserAvatar(filePath)
                Log.d("useravatar", uploadAvatar)
                if (uploadAvatar.isNotEmpty()) {
                    repository.saveUserAvatarToFirestore(currentUser!!, uploadAvatar)
                }
                viewModelScope.launch {
                    repository.uploadUserAvatarToRoom(context, uploadAvatar, currentUser!!)
                }
            }


//            val storageReference = Firebase.storage.reference.child("userAvatars/" + UUID.randomUUID().toString())
//            val uploadTask = storageReference.putFile(filePath)
//            val urlTask = uploadTask.continueWithTask { task ->
//                if (!task.isSuccessful) {
//                    task.exception?.let {
//                        throw it
//                    }
//                }
//                storageReference.downloadUrl
//            }.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                        val downloadUri = task.result.toString()
//                        Log.d("useravatar", downloadUri)
//                } else {
//                    // Handle failures
//                    // ...
//                }
//            }
        }
    }
}