package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import com.lukakordzaia.helpmeapp.network.model.Address
import com.lukakordzaia.helpmeapp.repository.UserAddressRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class UserAddressesViewModel : BaseViewModel() {
    private val repository = UserAddressRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _noAddress = MutableLiveData<Boolean>()
    private val _addressList = MutableLiveData<List<Address>>()

    val showProgress: LiveData<Boolean> = _showProgress
    val noAddress: LiveData<Boolean> = _noAddress
    val addressList: LiveData<List<Address>> = _addressList

    fun onAddNewAddressPressed() {
        navigateToNewFragment(UserAddressesFragmentDirections.actionUserAddressesFragmentToAddNewAddressFragment())
    }

    fun onSingleAddressPressed(addressId: String) {
        navigateToNewFragment(UserAddressesFragmentDirections.actionUserAddressesFragmentToSingleUserAddressFragment(addressId))
    }

    fun addAddressToFirestore(address: String) {
        viewModelScope.launch {
            val saveUserAddress = repository.addUserAddress(currentUserId()!!, address)
            if (saveUserAddress) {
                newToastMessage("მისამართი წარმატებით დაემატა")
            } else {
                newToastMessage("მისამართი ვერ დაემატა, გთხოვთ სცადოთ თავიდან")
            }
        }
    }

    fun deleteSingleAddress(addressId: String) {
        viewModelScope.launch {
            val deleteAddress = repository.deleteUserAddress(currentUserId()!!, addressId)
            if (deleteAddress) {
                newToastMessage("მისამართი წარმატებით წაიშალა")
            } else {
                newToastMessage("მისამართი არ წაიშალა, გთხვოთ სცადოთ თავიდან")
            }
        }
    }

    fun getUserAddresses() {
        _showProgress.value = false

        repository.getUserAddress(currentUserId()!!, object : FirestoreAddressesCallBack {
            override fun onCallback(addresses: MutableList<Address>) {
                val allAddresses: MutableList<Address> = ArrayList()
                if (!addresses.isNullOrEmpty()) {
                    addresses.forEach {
                        allAddresses.add(Address(it.id, it.address, it.details))
                    }
                    _noAddress.value = false
                    _addressList.value = allAddresses
                }
            }
        })
    }

}