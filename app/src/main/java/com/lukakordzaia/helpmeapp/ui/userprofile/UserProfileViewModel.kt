package com.lukakordzaia.helpmeapp.ui.userprofile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.User
import com.lukakordzaia.helpmeapp.repository.UserProfileRepository
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class UserProfileViewModel: ViewModel() {
    private val repository = UserProfileRepository()

    private val  _userFullName = MutableLiveData<String>()
    private val _userEmail = MutableLiveData<String>()
    private val _userAvatar = MutableLiveData<String>()
    private val _userNumber = MutableLiveData<String>()

    val userFullName: LiveData<String> = _userFullName
    val userEmail: LiveData<String> = _userEmail
    val userNumber: LiveData<String> = _userNumber
    val userAvatar: LiveData<String> = _userAvatar

    init {
        addUserChangeListener()
    }

    fun removeSaveToken() {
        AppPreferences.user_token = ""
        Firebase.auth.signOut()
    }

    private fun addUserChangeListener(){
        val dbReference = FirebaseDatabase.getInstance().getReference("Users")
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser!!.uid

        dbReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java) ?: return

                _userFullName.value = "${user.name} ${user.lastName}"
                _userEmail.value = user.email
                _userNumber.value = user.phone

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read user", error.toException())
            }
        })
    }
}