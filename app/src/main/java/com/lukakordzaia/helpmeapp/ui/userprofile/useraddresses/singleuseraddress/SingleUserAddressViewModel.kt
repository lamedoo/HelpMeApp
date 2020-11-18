package com.lukakordzaia.helpmeapp.ui.userprofile.useraddresses.singleuseraddress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.network.model.Address
import com.lukakordzaia.helpmeapp.repository.UserAddressRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class SingleUserAddressViewModel : BaseViewModel() {
    private val repository = UserAddressRepository()
    private val _addressInfo = MutableLiveData<Address>()

    val addressInfo: LiveData<Address> = _addressInfo

    fun getSingleAddress(addressId: String) {
        viewModelScope.launch {
            val singleAddress = repository.getSingleAddress(currentUserId()!!, addressId)
            if (singleAddress != null) {
                _addressInfo.value = Address(
                    singleAddress["id"].toString(),
                    singleAddress["address"].toString(),
                    singleAddress["details"].toString()
                )
            }
        }
    }

    fun addAddressDetails(addressId: String, addressDetails: String) {
        viewModelScope.launch {
            val addDetails = repository.addAddressDetails(currentUserId()!!, addressId, addressDetails)
            if (addDetails) {
                newToastMessage("მისამართს დეტალები განახლდა")
            } else {
                newToastMessage("დაფიქსირდა შეცდომა")
            }
        }
    }
}