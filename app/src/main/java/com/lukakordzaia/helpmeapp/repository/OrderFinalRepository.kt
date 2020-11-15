package com.lukakordzaia.helpmeapp.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lukakordzaia.helpmeapp.network.model.OrderDetails
import kotlinx.coroutines.tasks.await

class OrderFinalRepository {
    suspend fun createNewOrder(userName: String, orderDetails: OrderDetails): Boolean {
        return try {
            Firebase.firestore
                .collection("users")
                .document(userName)
                .collection("orders")
                .document()
                .set(mapOf(
                    "orderDate" to orderDetails.orderDate,
                    "orderAddress" to orderDetails.orderAddress,
                    "orderHelper" to "${orderDetails.orderHelper}, ${orderDetails.orderHelperId}",
                    "services" to mapOf(
                        "kitchen" to orderDetails.orderServices.kitchen,
                        "livingRoom" to orderDetails.orderServices.living,
                        "studio" to orderDetails.orderServices.studio,
                        "bedroom" to orderDetails.orderServices.bedroom,
                        "bathroom" to orderDetails.orderServices.bathroom,
                        "office" to orderDetails.orderServices.office
                    )
                ))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}