package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckOutPage : AppCompatActivity() {

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editStreetAddress: EditText
    private lateinit var editTownCity: EditText
    private lateinit var editState: EditText
    private lateinit var editCountry: EditText
    private lateinit var editPostalZip: EditText
    private lateinit var editPhone: EditText
    private lateinit var textProductName: TextView
    private lateinit var textQuantity: TextView
    private lateinit var buttonCheckout: Button
    private val userId = AuthManager.instance.getUserId() ?: -1 // Get the real user ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout)

        editFirstName = findViewById(R.id.name)
        editStreetAddress = findViewById(R.id.edit_street_address)
        editTownCity = findViewById(R.id.edit_town_city)
        editState = findViewById(R.id.edit_state)
        editCountry = findViewById(R.id.edit_country)
        editPostalZip = findViewById(R.id.edit_postal_zip)
        editPhone = findViewById(R.id.edit_phone)
        textProductName = findViewById(R.id.text_product_name)
        textQuantity = findViewById(R.id.text_quantity)
        buttonCheckout = findViewById(R.id.button_checkout)

        val productName = intent.getStringExtra("PRODUCT_NAME")
        val quantity = intent.getIntExtra("QUANTITY", 1)

        textProductName.text = productName
        textQuantity.text = "Quantity: $quantity"

        val returnButton: Button = findViewById(R.id.button_return_home)
        returnButton.setOnClickListener {
            val intent = Intent(this@CheckOutPage, HomePage::class.java)
            startActivity(intent)
            finish() // Optional: finish the checkout activity
        }

        buttonCheckout.setOnClickListener {
            userId
            val checkoutRequest = CheckoutRequest(
                editFirstName.text.toString(),
                editStreetAddress.text.toString(),
                editTownCity.text.toString(),
                editState.text.toString(),
                editCountry.text.toString(),
                editPostalZip.text.toString(),
                editPhone.text.toString(),
                "COD", // Assuming payment method is COD
                "true" // Assuming user accepts the terms
            )

            // Make the API call
            RetrofitClient.instance.checkout(userId, checkoutRequest)
                .enqueue(object : Callback<CheckoutResponse> {
                    override fun onResponse(call: Call<CheckoutResponse>, response: Response<CheckoutResponse>) {
                        if (response.isSuccessful) {
                            val checkoutResponse = response.body()
                            if (checkoutResponse != null) {
                                // Handle successful response
                                val orderId = checkoutResponse.order_id
                                val message = checkoutResponse.message
                                // Show success message
                                showToast("Order placed successfully. Order ID: $orderId")
                                // Log success message
                                Log.d("Checkout", "Order placed successfully. Order ID: $orderId")
                                navigateToOrdersSuccessPage() // Navigate to orders success page
                            } else {
                                // Handle null response body
                                showToast("Error: Response body is null")
                                Log.e("Checkout", "Error: Response body is null")
                            }
                        } else {
                            // Handle unsuccessful response
                            val errorBody = response.errorBody()?.string()
                            showToast("Error: ${response.code()} - $errorBody")
                            Log.e("Checkout", "Error: ${response.code()} - $errorBody")
                        }
                    }

                    override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                        // Handle network errors or unexpected errors
                        showToast("Network Error: ${t.message}")
                        Log.e("Checkout", "Network Error: ${t.message}", t)
                    }
                })
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this@CheckOutPage, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToOrdersSuccessPage() {
        val intent = Intent(this@CheckOutPage, OrdersSuccessPage::class.java)
        startActivity(intent)
        finish() // Finish the checkout activity
    }
}
