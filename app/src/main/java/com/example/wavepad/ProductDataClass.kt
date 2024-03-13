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
    val product_price:  String = "",
    val product_discount: Int = 0,
    val product_weight: Int = 0,
    val product_image:  String = "",
    val product_video: Int = 0,
    val description: String = "",
    val author: AuthorDataClass? = null,
    val attributes: List<Attribute> = listOf()
) :Serializable

data class AuthorDataClass(
    val id: Int = 0,
    val name: String = "",
    val status: Int = 0,
    val created_at: String? = null,
    val updated_at: String? = null
) : Serializable

data class Attribute(
    val id: Int = 0,
    val product_id: Int = 0,
    val size: String = "",
    val price: String = "",
    val stock: Int = 0,
    val sku: String = "",
    val status: Int = 0,
    val created_at: String = "",
    val updated_at: String = ""
) : Serializable