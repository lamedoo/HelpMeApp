package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.repository.HelperDetailRepository
import com.lukakordzaia.helpmeapp.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelperDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HelperDetailRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _showContent = MutableLiveData<Boolean>()
    private val _helperName = MutableLiveData<String>()
    private val _helperAvatar = MutableLiveData<String>()
    private val _helperBio = MutableLiveData<String>()
    private val _helperPrice = MutableLiveData<Int>()
    private val _helperRating = MutableLiveData<Int>()
    private val _helperJobsDone = MutableLiveData<Int>()

    val showProgress : LiveData<Boolean> = _showProgress
    val showContent: LiveData<Boolean> = _showContent
    val helperName : LiveData<String> = _helperName
    val helperAvatar : LiveData<String> = _helperAvatar
    val helperBio : LiveData<String> = _helperBio
    val helperPrice : LiveData<Int> = _helperPrice
    val helperRating : LiveData<Int> = _helperRating
    val helperJobsDone : LiveData<Int> = _helperJobsDone

    init {
        _showContent.value = false
    }

    fun getSingleHelper(helperId: Int) {
        viewModelScope.launch {
            when (val retrofit = repository.getSingleHelper(helperId)) {
                is Result.Success -> {
                    _showProgress.value = false
                    _showContent.value = true
                    _helperName.value = retrofit.data.name
                    _helperAvatar.value = retrofit.data.avatar
                    _helperBio.value = retrofit.data.bio
                    _helperPrice.value = retrofit.data.price
                    _helperRating.value = retrofit.data.rating
                    _helperJobsDone.value = retrofit.data.jobs_done
                }
                is Result.Error -> {
                    _showProgress.value = false
                    _showContent.value = false
                    Log.d("error", "error")
                }
            }
        }
    }
}