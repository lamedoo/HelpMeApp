package com.lukakordzaia.helpmeapp.ui.home.tophelper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakordzaia.helpmeapp.network.Result
import com.lukakordzaia.helpmeapp.network.model.Helpers
import com.lukakordzaia.helpmeapp.repository.HelperDetailRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class TopHelperDetailsViewModel : BaseViewModel() {
    private val repository = HelperDetailRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _helperData = MutableLiveData<Helpers>()

    val showProgress : LiveData<Boolean> = _showProgress
    val helperData : LiveData<Helpers> = _helperData

    init {
        AppPreferences.helper_name = ""
        AppPreferences.helper_id = ""
    }

    fun onOrderPressed(helperId: Int, helperName: String) {
        navigateToNewFragment(TopHelperDetailsFragmentDirections.actionTopHelperDetailsFragmentToOrderChooseDetailsFragment())
        AppPreferences.helper_name = helperName
        AppPreferences.helper_id = helperId.toString()
    }

    fun getTopHelper(helperId: Int) {
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