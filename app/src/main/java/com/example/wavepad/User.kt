package com.example.wavepad

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val email_verified_at: String?, // Nullable because it can be null in the JSON
    val gender: String,
    val age: Int,
    val created_at: String,
    val updated_at: String,
    val mobile: String,
    val status: String,
    val address: String?, // Nullable because it can be null in the JSON
    val city: String?, // Nullable because it can be null in the JSON
    val state: String?, // Nullable because it can be null in the JSON
    val country: String?, // Nullable because it can be null in the JSON
    val zipcode: String? // Nullable because it can be null in the JSON
//    val id: String,
//    val email: String,
//    val password: String
)
