package com.lukakordzaia.helpmeapp.ui.helpers

import android.util.Log
import androidx.lifecycle.*
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelpersRepository
import kotlinx.coroutines.launch

class HelpersViewModel : ViewModel() {
    private val repository = HelpersRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _helpersList =  MutableLiveData<List<Helpers>>()

    val showProgress : LiveData<Boolean> = _showProgress
    val helpersList : LiveData<List<Helpers>> = _helpersList

    fun getAllHelpers() {
        viewModelScope.launch {
            when (val retrofit = repository.getAllHelpers()) {
                is Result.Success -> {
                    _showProgress.value = false
                    _helpersList.postValue(retrofit.data)
                }
                is Result.Error -> {
                    _showProgress.value = false
                    Log.d("error", "error")
                }
            }
        }
    }
}