package com.example.wavepad

//ProductService Class:
//
//This class acts as an intermediary between your app and the REST API.
//It initializes the ProductApi interface using the RetrofitClient.
//The getProducts() function sends a GET request to fetch products asynchronously.
//It handles success and failure cases through Retrofit callbacks and invokes the callback passed as a parameter.


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductService {

//    private val api: ProductApi = RetrofitClient.createService(ProductApi::class.java)
//
//    fun getProducts(callback: (List<ProductDataClass>?) -> Unit) {
//        api.getProducts().enqueue(object : Callback<List<ProductDataClass>> {
//            override fun onResponse(call: Call<List<ProductDataClass>>, response: Response<List<ProductDataClass>>) {
//                if (response.isSuccessful) {
//                    val products = response.body()
//                    callback(products)
//                } else {
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<List<ProductDataClass>>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }
}
