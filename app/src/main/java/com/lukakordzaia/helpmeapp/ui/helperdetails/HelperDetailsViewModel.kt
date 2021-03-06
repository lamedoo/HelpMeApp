package com.lukakordzaia.helpmeapp.ui.helperdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelperDetailRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class HelperDetailsViewModel : BaseViewModel() {
    private val repository = HelperDetailRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _helperData = MutableLiveData<Helpers>()

    val showProgress : LiveData<Boolean> = _showProgress
    val helperData : LiveData<Helpers> = _helperData

    fun onOrderPressed(helperId: Int, helperName: String, cleaningOption: String, orderAddress: String, orderDate: String, serviceCount: IntArray) {
        navigateToNewFragment(HelperDetailsFragmentDirections.actionHelperDetailsFragmentToOrderCheckFinalFragment(
            cleaningOption,
            orderAddress,
            orderDate,
            helperName,
            helperId.toString(),
            serviceCount
        ))
    }

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