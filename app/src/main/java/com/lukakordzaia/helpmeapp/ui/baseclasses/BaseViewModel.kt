package com.lukakordzaia.helpmeapp.ui.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukakordzaia.helpmeapp.utils.Event

abstract class BaseViewModel : ViewModel() {
    private val _navigateScreen = MutableLiveData<Event<Int>>()
    private val _toastMessage = MutableLiveData<Event<String>>()

    val navigateScreen: LiveData<Event<Int>> = _navigateScreen
    val toastMessage: LiveData<Event<String>> = _toastMessage

    fun navigateToNewFragment(navId: Int) {
        _navigateScreen.value = Event(navId)
    }

    fun newToastMessage(message: String) {
        _toastMessage.value = Event(message)
    }

}