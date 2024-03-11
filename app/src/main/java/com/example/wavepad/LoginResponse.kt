package com.example.wavepad

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val token: String,
    val user: User
)
