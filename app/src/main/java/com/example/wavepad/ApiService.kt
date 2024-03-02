package com.example.wavepad

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("api/user/register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("mobile") mobile: String,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("status") status: Int
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/user/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("api/product/products")
    fun getProducts(): Call<ProductResponse>
}