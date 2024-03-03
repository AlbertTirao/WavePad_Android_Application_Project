package com.example.wavepad

//ProductService Class:
//
//This class acts as an intermediary between your app and the REST API.
//It initializes the ProductApi interface using the RetrofitClient.
//The getProducts() function sends a GET request to fetch products asynchronously.
//It handles success and failure cases through Retrofit callbacks and invokes the callback passed as a parameter.


//class ProductService(private val context: Context) {
//    fun getProducts(callback: (List<ProductDataClass>?) -> Unit) {
//        RetrofitClient.instance.getProducts().enqueue(object : Callback<List<ProductDataClass>> {
//            override fun onResponse(call: Call<List<ProductDataClass>>, response: Response<List<ProductDataClass>>) {
//                if (response.isSuccessful) {
//                    val products = response.body()
//                    callback(products)
//                } else {
//                    showToast("Error: ${response.code()}")
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<List<ProductDataClass>>, t: Throwable) {
//                showToast("Network request failed")
//                callback(null)
//            }
//        })
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
//}


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
                    Log.d("dddd", "wentherev2")
                    showToast("GUMANA")
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