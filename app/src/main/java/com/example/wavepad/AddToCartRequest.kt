package com.example.wavepad

data class AddToCartRequest(
    val user_id: Int,
    val product_id: Int,
    val size: String,
    val quantity: Int
)
