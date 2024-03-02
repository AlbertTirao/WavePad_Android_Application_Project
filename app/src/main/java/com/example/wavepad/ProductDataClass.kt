package com.example.wavepad

import java.io.Serializable

data class ProductDataClass(
    val id: Int = 0,
    val section_id: Int = 0,
    val category_id: String = "",
    val author_id: String = "",
    val vendor_id: String = "",
    val admin_id: Int = 0,
    val admin_type: String = "",
    val product_name: String = "",
    val product_code: String = "",
    val product_color: String = "",
    val product_price: Int = 0,
    val product_discount: Int = 0,
    val product_weight: Int = 0,
    val product_image: Int = 0,
    val product_video: Int = 0,
    val description: String = "",
)
:Serializable
