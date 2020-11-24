package com.lukakordzaia.helpmeapp.ui.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lukakordzaia.helpmeapp.repository.AuthRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {
    private val repository = AuthRepository()


    fun userLogin(auth: FirebaseAuth, email: String, password: String, name: String, lastName: String, phone: String) {
        viewModelScope.launch {
            val result = repository.register(auth, email, password, name, lastName, phone)
            if (result != null) {
                newToastMessage("თქვენ წარმატებით დარეგისტრირდით, გთხოვ გაიაროთ ავტორიზაცია")
                navigateToNewFragment(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            } else {
                newToastMessage("სამწუხაროდ, რეგისტრაცია ვერ მოხერხდა. გთხოვთ სცადოთ თავიდან")
            }
        }
    }
}