package com.example.wavepad

//RetrofitClient Object:
//This singleton object encapsulates the creation of Retrofit instances.
//It sets up the base URL, logging interceptor, OkHttpClient, and Gson converter factory.
//It provides a generic method createService() to create service instances for different Retrofit interfaces.

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://wavepad-ecom-529a3cf49f8f.herokuapp.com/"
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiService::class.java)
    }
}
