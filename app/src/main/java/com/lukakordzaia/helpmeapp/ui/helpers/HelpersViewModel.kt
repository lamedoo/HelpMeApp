package com.lukakordzaia.helpmeapp.ui.helpers

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
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
import java.util.*

class HelpersViewModel : BaseViewModel() {
    private val repository = HelpersRepository()
    private val _showProgress = MutableLiveData<Boolean>()
    private val _helpersList =  MutableLiveData<List<Helpers>>()
    private val _pickedDate = MutableLiveData<String>("აირჩიეთ თარიღი")

    val showProgress : LiveData<Boolean> = _showProgress
    val helpersList : LiveData<List<Helpers>> = _helpersList
    val pickedDate : LiveData<String> = _pickedDate

    init {
        getAllHelpers()
    }

    fun onDatePickerPressed(context: Context) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context, { view, yearCurrent, monthOfYear, dayOfMonth ->
            _pickedDate.value = "$dayOfMonth/${monthOfYear + 1}/$yearCurrent"
        }, year, month, day)

        dpd.apply {
            this.show()
            this.datePicker.minDate = (System.currentTimeMillis() + 48*60*60*1000)
            this.datePicker.maxDate = (System.currentTimeMillis() + 14*24*60*60*1000)
            this.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF018786"))
            this.getButton(DatePickerDialog.BUTTON_POSITIVE).text = "არჩევა"
            this.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF018786"))
            this.getButton(DatePickerDialog.BUTTON_NEGATIVE).text = "უკან"
        }
    }

    fun onHelperPressed(helperId: Int, cleaningOption: String, orderAddress: String, orderDate: String, serviceCount: IntArray) {
        if (pickedDate.value == "აირჩიეთ თარიღი") {
            newToastMessage("გთხოვთ ჯერ აირჩიოთ თარიღი")
        } else {
            navigateToNewFragment(HelpersFragmentDirections.actionHelpersFragmentToHelperDetailsFragment(
                helperId,
                cleaningOption,
                orderAddress,
                orderDate,
                serviceCount
            ))
        }
    }

    fun getAllHelpers() {
        viewModelScope.launch {
            when (val retrofit = repository.getAllHelpers()) {
                is Result.Success -> {
                    _showProgress.value = false
                    _helpersList.value = retrofit.data

                    if (AppPreferences.helper_id != "") {
                        val data = helpersList.value?.filter {
                            it.name.contains(AppPreferences.helper_name, true)
                        }
                        _helpersList.value = data
                    }
                }
                is Result.Error -> {
                    _showProgress.value = false
                    Log.d("error", "error")
                }
            }
        }
    }

    fun getSingleTopHelper() {
        if (AppPreferences.helper_id != "") {
            val data = helpersList.value?.filter {
                it.name.contains("brad", true)
            }
            _helpersList.value = data
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