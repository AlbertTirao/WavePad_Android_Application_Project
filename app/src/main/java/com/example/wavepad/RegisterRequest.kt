package com.example.wavepad

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val mobile: String,
    val age: Int,
    val gender: String,
    val status: Int
)
