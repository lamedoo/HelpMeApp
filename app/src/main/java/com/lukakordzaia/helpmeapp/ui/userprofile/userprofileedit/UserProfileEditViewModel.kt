package com.lukakordzaia.helpmeapp.ui.userprofile.userprofileedit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.model.UserRegister
import com.lukakordzaia.helpmeapp.network.model.UserUpdate
import com.lukakordzaia.helpmeapp.repository.UserProfileEditRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class UserProfileEditViewModel : BaseViewModel() {
    private val repository = UserProfileEditRepository()
    private val _userDataList = MutableLiveData<UserRegister>()

    val userDataList: LiveData<UserRegister> = _userDataList

    init {
        getUserEditData()
    }


    private fun getUserEditData(){
        repository.getUserEditData(currentUserId()!!, object: FirebaseCallBack {
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
        val userData = UserUpdate(email, name, lastName, number)
        viewModelScope.launch {
            val update = currentUserId()?.let { repository.updateUserData(it, userData) }
            if (update == true) {
                newToastMessage("პროფილი წარმატებით განახლდა")
            }
        }
        viewModelScope.launch {
            if (currentUserId() != null) {
                repository.updateUserDataToRoom(context, currentUserId()!!, userData)
            }
        }

        if (filePath != null) {
            viewModelScope.launch {
                val uploadAvatar = repository.uploadUserAvatar(filePath)
                newToastMessage("სურათი წარმატებით აიტვირთა")
                if (uploadAvatar.isNotEmpty()) {
                    repository.saveUserAvatarToFirestore(currentUserId()!!, uploadAvatar)
                }
                viewModelScope.launch {
                    repository.uploadUserAvatarToRoom(context, uploadAvatar, currentUserId()!!)
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