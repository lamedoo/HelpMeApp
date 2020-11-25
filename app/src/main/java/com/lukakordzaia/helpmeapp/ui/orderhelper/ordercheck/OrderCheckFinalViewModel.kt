package com.lukakordzaia.helpmeapp.ui.orderhelper.ordercheck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.model.OrderDetails
import com.lukakordzaia.helpmeapp.repository.OrderFinalRepository
import com.lukakordzaia.helpmeapp.ui.baseclasses.BaseViewModel
import kotlinx.coroutines.launch

class OrderCheckFinalViewModel : BaseViewModel() {
    private val repository = OrderFinalRepository()
    private val _orderDetails = MutableLiveData<OrderDetails>()

    val orderDetails: LiveData<OrderDetails> = _orderDetails

    fun finalOrderDetails(orderDetails: OrderDetails) {
        _orderDetails.value = orderDetails
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