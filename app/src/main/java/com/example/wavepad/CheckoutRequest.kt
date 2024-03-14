package com.example.wavepad

data class CheckoutRequest(
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val country: String,
    val zipcode: String,
    val mobile: String,
    val payment_gateway: String,
    val accept: String
)
