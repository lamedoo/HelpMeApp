package com.lukakordzaia.helpmeapp.ui.userprofile.userprofile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirebaseCallBack
import com.lukakordzaia.helpmeapp.network.model.UserRegister
import com.lukakordzaia.helpmeapp.network.room.HelpMeAppDatabase
import com.lukakordzaia.helpmeapp.network.room.Users
import com.lukakordzaia.helpmeapp.repository.UserProfileRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences

class UserProfileViewModel: BaseViewModel() {
    private val repository = UserProfileRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _userDataList = MutableLiveData<UserRegister>()

    val showProgress: LiveData<Boolean> = _showProgress
    val userDataList: LiveData<UserRegister> = _userDataList

    fun onAddressesPressed() {
        navigateToNewFragment(UserProfileFragmentDirections.actionUserProfileFragmentToUserAddressesFragment())
    }

    fun onUserEditPressed() {
        navigateToNewFragment(UserProfileFragmentDirections.actionUserProfileFragmentToUserProfileEditFragment())
    }

    fun removeSaveToken() {
        AppPreferences.user_token = ""
        Firebase.auth.signOut()
    }

    fun getUserDataFromRoom(context: Context) : LiveData<Users> {
        val currentUser = Firebase.auth.currentUser?.uid
        val roomDBUserData = HelpMeAppDatabase.getDatabase(context)?.getDao()
        _showProgress.value = false

        return repository.getUserDataFromRoom(roomDBUserData!!, currentUser!!)
    }

    fun addUserChangeListener(context: Context){
        val currentUser = Firebase.auth.currentUser?.uid

        repository.getUserData(context, currentUser!!, object: FirebaseCallBack {
            override fun onCallback(userData: MutableMap<String, Any>) {
                _showProgress.value = false
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
}