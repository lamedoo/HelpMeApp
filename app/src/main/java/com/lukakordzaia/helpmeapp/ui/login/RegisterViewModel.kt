package com.lukakordzaia.helpmeapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.repository.AuthRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {
    private val repository = AuthRepository()
    private val _registerSuccess = MutableLiveData<Boolean>()

    val registerSuccess: LiveData<Boolean> = _registerSuccess


    fun userLogin(auth: FirebaseAuth, email: String, password: String, name: String, lastName: String, phone: String) {
        viewModelScope.launch {
            val result = repository.register(auth, email, password, name, lastName, phone)
            if (result != null) {
                _registerSuccess.value = true
                newToastMessage("თქვენ წარმატებით დარეგისტრირდით, გთხოვ გაიაროთ ავტორიზაცია")
                navigateToNewFragment(R.id.action_registerFragment_to_loginFragment)
            } else {
                _registerSuccess.value = false
                newToastMessage("სამწუხაროდ, რეგისტრაცია ვერ მოხერხდა. გთხოვთ სცადოთ თავიდან")
            }
        }
    }
}