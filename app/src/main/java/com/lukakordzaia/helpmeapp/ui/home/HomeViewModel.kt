package com.lukakordzaia.helpmeapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.R
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelpersRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel: BaseViewModel() {
    private val repository = HelpersRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _topHelpersList =  MutableLiveData<List<Helpers>>()

    val showProgress : LiveData<Boolean> = _showProgress
    val topHelpersList : LiveData<List<Helpers>> = _topHelpersList

    fun onHelpersPressed() {
        navigateToNewFragment(R.id.action_homeFragment_to_helperDetailsFragment)
    }

    fun onHelpersListPressed() {
        navigateToNewFragment(R.id.action_homeFragment_to_helpersFragment)
    }

    fun onSettingsPressed() {
        navigateToNewFragment(R.id.action_homeFragment_to_settingsFragment)
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