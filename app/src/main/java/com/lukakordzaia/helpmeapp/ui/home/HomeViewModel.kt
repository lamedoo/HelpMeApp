package com.lukakordzaia.helpmeapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelpersRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {
    private val repository = HelpersRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _topHelpersList =  MutableLiveData<List<Helpers>>()

    val showProgress : LiveData<Boolean> = _showProgress
    val topHelpersList : LiveData<List<Helpers>> = _topHelpersList

    init {
        AppPreferences.helper_name = ""
        AppPreferences.helper_id = ""
        AppPreferences.order_date = ""
        AppPreferences.order_address = ""
        AppPreferences.service_kitchen = 0
        AppPreferences.service_living = 0
        AppPreferences.service_studio = 0
        AppPreferences.service_bedroom = 0
        AppPreferences.service_bathroom = 0
        AppPreferences.service_office = 0
    }

    fun onHelpersPressed(helperId: Int) {
        navigateToNewFragment(HomeFragmentDirections.actionHomeFragmentToTopHelperDetailsFragment(helperId))
    }

    fun onChooseServicesPressed(cleaningOption: String) {
        navigateToNewFragment(HomeFragmentDirections.actionHomeFragmentToOrderChooseDetailsFragment())
        AppPreferences.order_cleaning_option = cleaningOption
    }

    fun onSettingsPressed() {
        navigateToNewFragment(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
    }

    fun getTopHelpers() {
        viewModelScope.launch {
            when (val retrofit = repository.getAllHelpers()) {
                is Result.Success -> {
                    _showProgress.value = false
                    val data = retrofit.data.filter {
                        it.rating >= 80
                    }
                    _topHelpersList.postValue(data)
                }
                is Result.Error -> {
                    _showProgress.value = false
                    Log.d("error", "error")
                }
            }
        }
    }
}