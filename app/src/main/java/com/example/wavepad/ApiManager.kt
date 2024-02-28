//package com.example.wavepad
//
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class ApiManager {
//    private val BASE_URL = "https://wavepad-ecom-529a3cf49f8f.herokuapp.com/"
//    private val loggingInterceptor = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
//        .build()
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(client)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private val apiService = retrofit.create(ApiService::class.java)
//
//    fun signUp(registerRequest: RegisterRequest, callback: retrofit2.Callback<RegisterResponse>) {
//        val call = apiService.registerUser(registerRequest)
//        call.enqueue(callback)
//    }
//
//    fun login(email: String, password: String, callback: retrofit2.Callback<LoginResponse>) {
//        val call = apiService.loginUser(LoginRequest(email, password))
//        call.enqueue(callback)
//    }
//
//    // Define your ApiService interface here
//}
//
//// Define your data classes and ApiService interface here
