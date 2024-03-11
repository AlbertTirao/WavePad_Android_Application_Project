package com.example.wavepad

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("product_id") val productId: Int,
    val size: String,
    val quantity: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

