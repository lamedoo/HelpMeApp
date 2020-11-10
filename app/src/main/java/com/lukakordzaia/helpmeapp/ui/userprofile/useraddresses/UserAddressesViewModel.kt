package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import com.lukakordzaia.helpmeapp.repository.UserProfileEditRepository
import kotlinx.coroutines.launch

class UserAddressesViewModel : ViewModel() {
    private val repository = UserProfileEditRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _noAddress = MutableLiveData<Boolean>()
    private val _addressList = MutableLiveData<List<String>>()

    val showProgress: LiveData<Boolean> = _showProgress
    val noAddress: LiveData<Boolean> = _noAddress
    val addressList: LiveData<List<String>> = _addressList

    fun addAddressToFirestore(address: String) {
        val currentUser = Firebase.auth.currentUser?.uid
        viewModelScope.launch {
            repository.saveUserAddress(currentUser!!, address)
        }
    }

    fun getUserAddresses() {
        val currentUser = Firebase.auth.currentUser?.uid
        _showProgress.value = false

        repository.getUserAddress(currentUser!!, object : FirestoreAddressesCallBack {
            override fun onCallback(addresses: MutableList<*>) {
                val allAddresses: MutableList<String> = ArrayList()
                if (!addresses.isNullOrEmpty()) {
                    addresses.forEach {
                        allAddresses.add(it.toString())
                    }
                    _noAddress.value = false
                    _addressList.value = allAddresses
                }
            }
        })
    }

}