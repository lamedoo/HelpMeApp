package com.lukakordzaia.helpmeapp.ui.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.lukakordzaia.helpmeapp.utils.Event

abstract class BaseViewModel : ViewModel() {
    private val _navigateScreen = MutableLiveData<Event<NavDirections>>()
    private val _toastMessage = MutableLiveData<Event<String>>()
    private val _chosenAddress = MutableLiveData<Event<String?>>()

    val navigateScreen: LiveData<Event<NavDirections>> = _navigateScreen
    val toastMessage: LiveData<Event<String>> = _toastMessage
    val chosenAddress: LiveData<Event<String?>> = _chosenAddress

    fun navigateToNewFragment(navId: NavDirections) {
        _navigateScreen.value = Event(navId)
    }

    fun newToastMessage(message: String) {
        _toastMessage.value = Event(message)
    }

    fun chooseNewAddress(address: String?) {
        _chosenAddress.value = Event(address)
    }
}