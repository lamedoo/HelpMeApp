package com.lukakordzaia.helpmeapp.network.model

data class OrderDetails (
    val orderCleaningOption: String,
    val orderDate: String,
    val orderAddress: String,
    val orderHelper: String,
    val orderHelperId: String,
    val orderServices: Services
) {
    data class Services (
        val kitchen: Int,
        val living: Int,
        val studio: Int,
        val bedroom: Int,
        val bathroom: Int,
        val office: Int
    )
}