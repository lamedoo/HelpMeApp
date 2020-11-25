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
        AppPreferences.helper_id = ""
        AppPreferences.helper_name = ""
    }

    fun onHelpersPressed(helperId: Int) {
        navigateToNewFragment(HomeFragmentDirections.actionHomeFragmentToTopHelperDetailsFragment(helperId))
    }

    fun onChooseServicesPressed(cleaningOption: String) {
        navigateToNewFragment(HomeFragmentDirections.actionHomeFragmentToOrderChooseDetailsFragment(cleaningOption))
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