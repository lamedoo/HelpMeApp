package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchooseservices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences

class OrderChooseServicesViewModel : BaseViewModel() {
    private var _servicesList = MutableLiveData<List<String>>()

    val servicesList: LiveData<List<String>> = _servicesList

    init {
        setServicesList()
        AppPreferences.service_kitchen = 0
        AppPreferences.service_living = 0
        AppPreferences.service_studio = 0
        AppPreferences.service_bedroom = 0
        AppPreferences.service_bathroom = 0
        AppPreferences.service_office = 0

    }

    fun checkServices() {
        val kitchen = AppPreferences.service_kitchen
        val living = AppPreferences.service_living
        val studio = AppPreferences.service_studio
        val bedroom = AppPreferences.service_bedroom
        val bathroom = AppPreferences.service_bathroom
        val office = AppPreferences.service_office
        if (kitchen == 0 && living == 0 && studio == 0 && bedroom == 0 && bathroom == 0 && office == 0){
            newToastMessage("გთხოვთ, აირჩიოთ მინიმუმ ერთი ოთახი")
        } else {
            navigateToNewFragment(OrderChooseServicesFragmentDirections.actionOrderChooseServicesFragmentToOrderCheckFinalFragment())
        }
    }

    fun getServiceAmounts(serviceName: String, amount: Int) {
        when (serviceName) {
            "სამზარეულო" -> AppPreferences.service_kitchen = amount
            "მისაღები" -> AppPreferences.service_living = amount
            "სტუდიო" -> AppPreferences.service_studio = amount
            "საძინებელი" -> AppPreferences.service_bedroom = amount
            "სააბაზანო" -> AppPreferences.service_bathroom = amount
            "კაბინეტი" -> AppPreferences.service_office = amount
            else -> Log.d("service", "არაფერია")
        }
    }

    private fun setServicesList() {
        val services: MutableList<String> = mutableListOf(
            "სამზარეულო",
            "მისაღები",
            "სტუდიო",
            "საძინებელი",
            "სააბაზანო",
            "კაბინეტი")
        _servicesList.value = services
    }
}