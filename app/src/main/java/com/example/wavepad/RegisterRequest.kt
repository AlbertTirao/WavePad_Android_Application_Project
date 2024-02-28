package com.example.wavepad

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val mobilePhone: String,
    val gender: String,
    val age: Int,
    val status: Int
)
