package com.example.wavepad

data class CartItemResponse(
    val product_name: String,
    val product_price: Double,
    val size: String,
    val quantity: String,
    val total: Double
)
