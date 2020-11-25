package com.lukakordzaia.helpmeapp.ui.orderhelper.orderchooseservices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lukakordzaia.helpmeapp.network.model.ServicesList
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel

class OrderChooseServicesViewModel : BaseViewModel() {
    private var _servicesList = MutableLiveData<MutableList<ServicesList>>()
    private var kitchenCount = MutableLiveData<Int>(0)
    private var livingCount = MutableLiveData<Int>(0)
    private var studioCount = MutableLiveData<Int>(0)
    private var bedroomCount = MutableLiveData<Int>(0)
    private var bathroomCount = MutableLiveData<Int>(0)
    private var officeCount = MutableLiveData<Int>(0)

    val servicesList: LiveData<MutableList<ServicesList>> = _servicesList

    fun checkServices(cleaningOption: String, orderAddress: String) {
        val kitchen = kitchenCount.value
        val living = livingCount.value
        val studio = studioCount.value
        val bedroom = bedroomCount.value
        val bathroom = bathroomCount.value
        val office = officeCount.value
        if (kitchen == 0 && living == 0 && studio == 0 && bedroom == 0 && bathroom == 0 && office == 0){
            newToastMessage("გთხოვთ, აირჩიოთ მინიმუმ ერთი ოთახი")
        } else {
            navigateToNewFragment(OrderChooseServicesFragmentDirections.actionOrderChooseServicesFragmentToHelpersFragment(
                cleaningOption,
                orderAddress,
                intArrayOf(
                    kitchenCount.value!!,
                    livingCount.value!!,
                    studioCount.value!!,
                    bedroomCount.value!!,
                    bathroomCount.value!!,
                    officeCount.value!!
                )
            ))
        }
    }

    fun getServiceAmounts(serviceName: String, amount: Int) {
        when (serviceName) {
            "სამზარეულო" -> kitchenCount.value = amount
            "მისაღები" -> livingCount.value = amount
            "სტუდიო" -> studioCount.value = amount
            "საძინებელი" -> bedroomCount.value = amount
            "სააბაზანო" -> bathroomCount.value = amount
            "კაბინეტი" -> officeCount.value = amount
            else -> Log.d("service", "არაფერია")
        }
    }

    fun setServicesList() {
        val kitchen = ServicesList("სამზარეულო", kitchenCount.value!!)
        val living = ServicesList("მისაღები", livingCount.value!!)
        val studio = ServicesList("სტუდიო", studioCount.value!!)
        val bedroom = ServicesList("საძინებელი", bedroomCount.value!!)
        val bathroom = ServicesList("სააბაზანო", bathroomCount.value!!)
        val office = ServicesList("კაბინეტი", officeCount.value!!)

        _servicesList.value = mutableListOf(
            kitchen,
            living,
            studio,
            bedroom,
            bathroom,
            office
        )
    }
}