package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelperDetailRepository
import kotlinx.coroutines.launch

class HelperDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HelperDetailRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _helperData = MutableLiveData<Helpers>()

    val showProgress : LiveData<Boolean> = _showProgress
    val helperData : LiveData<Helpers> = _helperData

    fun getSingleHelper(helperId: Int) {
        viewModelScope.launch {
            when (val retrofit = repository.getSingleHelper(helperId)) {
                is Result.Success -> {
                    val data = retrofit.data
                    _showProgress.value = false
                    _helperData.value = Helpers(
                        data.id,
                        data.name,
                        data.avatar,
                        data.email,
                        data.bio,
                        data.price,
                        data.rating,
                        data.jobs_done,
                        data.city,
                        data.address
                    )
                }
                is Result.Error -> {
                    _showProgress.value = false
                    Log.d("error", retrofit.exception)
                }
            }
        }
    }
}