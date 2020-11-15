package com.lukakordzaia.helpmeapp.ui.orderhelper.ordercheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.model.OrderDetails
import com.lukakordzaia.helpmeapp.repository.OrderFinalRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import com.lukakordzaia.helpmeapp.utils.AppPreferences
import kotlinx.coroutines.launch

class OrderCheckFinalViewModel : BaseViewModel() {
    private val repository = OrderFinalRepository()
    private val _orderDetails = MutableLiveData<OrderDetails>()

    val orderDetails: LiveData<OrderDetails> = _orderDetails

    init {
        _orderDetails.value = OrderDetails(
            AppPreferences.order_date,
            AppPreferences.order_address,
            AppPreferences.helper_name,
            AppPreferences.helper_id,
            OrderDetails.Services(
                AppPreferences.service_kitchen,
                AppPreferences.service_living,
                AppPreferences.service_studio,
                AppPreferences.service_bedroom,
                AppPreferences.service_bathroom,
                AppPreferences.service_office
            )
        )
    }

    fun createNewOrder() {
        val currentUser = Firebase.auth.currentUser!!.uid
        viewModelScope.launch {
            orderDetails.value?.let {
                val addOrder = repository.createNewOrder(currentUser, it)
                if (addOrder) {
                    newToastMessage("შეკვეთა წარმატებით შესრულდა")
                    navigateToNewFragment(OrderCheckFinalFragmentDirections.actionOrderCheckFinalFragmentToHomeFragment())
                } else {
                    newToastMessage("დაფიქსირდა შეცდომა, სცადეთ თავიდან")
                }
            }
        }
    }
}