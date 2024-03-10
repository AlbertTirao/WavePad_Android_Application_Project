package com.example.wavepad

data class CartItemResponse(
    val product_name: String,
    val product_price: String,
    val size: String,
    val quantity: String,
    val total: Int
)
