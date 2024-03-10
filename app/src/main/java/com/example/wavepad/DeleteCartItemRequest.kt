package com.example.wavepad

data class DeleteCartItemRequest(
    val user_id: Int,
    val product_id: Int,
    val size: String
)
