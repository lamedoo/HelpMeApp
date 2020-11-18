package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchoosedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import com.lukakordzaia.helpmeapp.network.model.Address
import com.lukakordzaia.helpmeapp.repository.UserAddressRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences

class OrderChooseDetailsViewModel : BaseViewModel() {
    private val repository = UserAddressRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _noAddress = MutableLiveData<Boolean>()
    private val _addressList = MutableLiveData<List<Address>>()

    val showProgress: LiveData<Boolean> = _showProgress
    val noAddress: LiveData<Boolean> = _noAddress
    val addressList: LiveData<List<Address>> = _addressList


    fun saveChosenDateAddress(address: String) {
        AppPreferences.order_address = address
    }

    fun onAddAddressPressed() {
        navigateToNewFragment(OrderChooseDetailsFragmentDirections.actionOrderChooseDetailsFragmentToAddNewAddressFragment())
    }

    fun onSliderCompleted() {
        navigateToNewFragment(OrderChooseDetailsFragmentDirections.actionOrderChooseDetailsFragmentToOrderChooseServicesFragment())
    }

    fun addressNameNull() {
        chosenAddressName("")
    }

    fun chosenAddressName(name: String?) {
        chooseNewAddress(name)
    }

    fun getUserAddresses() {
        val currentUser = Firebase.auth.currentUser?.uid
        _showProgress.value = false

        repository.getUserAddress(currentUser!!, object : FirestoreAddressesCallBack {
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