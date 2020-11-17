package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchoosedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import com.lukakordzaia.helpmeapp.repository.OrderChooseDetailsRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences

class OrderChooseDetailsViewModel : BaseViewModel() {
    private val repository = OrderChooseDetailsRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _noAddress = MutableLiveData<Boolean>()
    private val _addressList = MutableLiveData<List<String>>()

    val showProgress: LiveData<Boolean> = _showProgress
    val noAddress: LiveData<Boolean> = _noAddress
    val addressList: LiveData<List<String>> = _addressList


    fun saveChosenDateAddress(address: String) {
//        AppPreferences.order_date = date
        AppPreferences.order_address = address
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