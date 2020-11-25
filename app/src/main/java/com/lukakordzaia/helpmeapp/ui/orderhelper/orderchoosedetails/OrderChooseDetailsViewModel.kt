package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchoosedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukakordzaia.helpmeapp.network.FirestoreAddressesCallBack
import com.lukakordzaia.helpmeapp.network.model.Address
import com.lukakordzaia.helpmeapp.repository.UserAddressRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel

class OrderChooseDetailsViewModel : BaseViewModel() {
    private val repository = UserAddressRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _noAddress = MutableLiveData<Boolean>()
    private val _addressList = MutableLiveData<List<Address>>()
    private val _chosenAddress = MutableLiveData<Address>(null)

    val showProgress: LiveData<Boolean> = _showProgress
    val noAddress: LiveData<Boolean> = _noAddress
    val addressList: LiveData<List<Address>> = _addressList
    val chosenAddress: LiveData<Address> = _chosenAddress

    fun setChosenAddress(address: Address) {
        _chosenAddress.value = address
    }

    fun onAddAddressPressed() {
        navigateToNewFragment(OrderChooseDetailsFragmentDirections.actionOrderChooseDetailsFragmentToAddNewAddressFragment())
    }

    fun onNextButtonPressed(cleaningOption: String, orderAddress: String) {
        navigateToNewFragment(OrderChooseDetailsFragmentDirections.actionOrderChooseDetailsFragmentToOrderChooseServicesFragment(cleaningOption, orderAddress))
    }

    fun getUserAddresses() {
        if (addressList.value == null) {
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
}