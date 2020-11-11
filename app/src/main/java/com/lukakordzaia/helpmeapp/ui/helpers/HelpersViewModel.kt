package com.lukakordzaia.helpmeapp.ui.helpers

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

class HelpersViewModel : BaseViewModel() {
    private val repository = HelpersRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _helpersList =  MutableLiveData<List<Helpers>>()

    val showProgress : LiveData<Boolean> = _showProgress
    val helpersList : LiveData<List<Helpers>> = _helpersList

    fun onHelperPressed() {
        navigateToNewFragment(R.id.action_helpersFragment_to_helperDetailsFragment)
    }

    fun getAllHelpers() {
        viewModelScope.launch {
            when (val retrofit = repository.getAllHelpers()) {
                is Result.Success -> {
                    _showProgress.value = false
                    _helpersList.value = retrofit.data
                }
                is Result.Error -> {
                    _showProgress.value = false
                    Log.d("error", "error")
                }
            }
        }
    }

    fun getHelpersByRating() {
        val data = helpersList.value?.sortedByDescending {
            it.rating
        }
        _helpersList.value = data
    }

    fun getHelpersByPrice() {
        val data = helpersList.value?.sortedWith(compareBy {
            it.price
        })
        _helpersList.value = data
    }

    fun filterWithSearch(searchWord: String) {
        val data = helpersList.value?.filter {
            it.name.contains(searchWord, true)
        }
        _helpersList.value = data
    }
}