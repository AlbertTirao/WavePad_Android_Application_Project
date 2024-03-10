package com.example.wavepad

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE;
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST("/api/user/getCart")
    suspend fun addToCart(@Body request: AddToCartRequest): Response<ResponseBody>

    // Add a new GET request to fetch cart items by user ID
    @GET("/api/user/{userId}/carts")
    suspend fun getCartItemsByUserId(@Path("userId") userId: Int): Response<GetCartItemsResponse>

    // Add a new DELETE request to delete a cart item
//    @DELETE("/api/user/deleteCart")
//    suspend fun deleteCartItem(@Body request: DeleteCartItemRequest): Response<ResponseBody>

    @HTTP(method = "DELETE", path = "/api/user/cart/delete", hasBody = true)
    suspend fun deleteCartItem(
        @Query("user_id") userId: Int,
        @Query("product_name") productName: String
    ): Response<ResponseBody>

}