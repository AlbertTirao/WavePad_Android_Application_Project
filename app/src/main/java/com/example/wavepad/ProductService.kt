package com.example.wavepad

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProductService(private val context: Context) {

    fun getProducts(callback: (List<ProductDataClass>?) -> Unit) {
        Log.d("dddd", "wenthere")
        RetrofitClient.instance.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    val products = productResponse?.products
                    callback(products)
                } else {
                    showToast("Error: ${response.code()}")
                    Log.d("dddd", response.toString())
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                if (t is IOException) {
                    showToast("Network request failed")
                } else {
                    Log.e("ProductService", "Unexpected error occurred", t)
                    Log.d("dddd", t.toString())
                    showToast("Unexpected error occurred")
                }
                callback(null)
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}