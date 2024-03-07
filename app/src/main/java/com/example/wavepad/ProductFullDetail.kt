package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductFullDetail: AppCompatActivity() {
    private var quantity: Int = 1
    private lateinit var product: ProductDataClass
//    private val apiService = RetrofitClient.createService(ApiService::class.java)
    val apiService = RetrofitClient.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_full_detail)

        val buyNowButton: Button = findViewById(R.id.BuyNow)
        val quantityTextView: TextView = findViewById(R.id.text_quantity)
        val minusButton: ImageButton = findViewById(R.id.btn_minus)
        val plusButton: ImageButton = findViewById(R.id.btn_plus)
        val productNameTextView: TextView = findViewById(R.id.text_product_name)
        //val productPriceTextView: TextView = findViewById(R.id.text_product_price)
        val productDescriptionTextView: TextView = findViewById(R.id.descriptiontext)
        val productImageView: ImageView = findViewById(R.id.image_product)
        val addToCartButton: ImageButton = findViewById(R.id.AddToCart)
        val returnButton: ImageButton = findViewById(R.id.returnback)

        quantity = intent.getIntExtra("QUANTITY", 1)

        product = intent.getSerializableExtra("PRODUCT") as ProductDataClass? ?: ProductDataClass()
        product?.let {
            productNameTextView.text = it.product_name
            productDescriptionTextView.text = it.description
//            productImageView.setImageResource(it.product_image)
        }

        returnButton.setOnClickListener {
            val signUpIntent = Intent(this@ProductFullDetail, HomePage::class.java)
            startActivity(signUpIntent)
        }

        updatePrice()

        quantityTextView.text = "$quantity"

        minusButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                quantityTextView.text = "$quantity"
                updatePrice()
            }
        }

        plusButton.setOnClickListener {
            quantity++
            quantityTextView.text = "$quantity"
            updatePrice()
        }

        addToCartButton.setOnClickListener {
            val userId = 18 // Dummy user ID for testing
            val productId = 6 // Replace with actual product ID//5 inaro ni bert, 6 ocakes
            val size = "4.25 x 6.87 inch" // Replace with actual size
            val request = AddToCartRequest(userId, productId, size, quantity)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.addToCart(request)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            // Handle success
                            val successMessage = "Product added to cart successfully"
                            // Update UI or show a toast message with successMessage
                            Toast.makeText(applicationContext, successMessage, Toast.LENGTH_SHORT).show()
                        } else {
                            // Handle failure
                            val errorBody = response.errorBody()?.string()
                            Log.e("Error", "Error add cart: $errorBody") // Log response body
                            // Display error message to the user
                            val errorMessage = "Failed add cart: $errorBody"
                            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    // Handle exception
                    val errorMessage = "Error add cart: ${e.message}"
                    Log.e("Error", errorMessage)
                    // Display error message to the user
                    Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        buyNowButton.setOnClickListener {
            val checkoutIntent = Intent(this@ProductFullDetail, CheckOutPage::class.java).apply {
                putExtra("PRODUCT", product)
                putExtra("QUANTITY", quantity)
            }
            startActivity(checkoutIntent)

            Log.d("ProductFullDetail", "Navigating to CheckoutPage")
        }
    }

    private fun updatePrice() {
        val totalPrice = product?.product_price?.toDouble() ?: 0.0 * quantity
        val formattedPrice = String.format("%.2f", totalPrice) // Format price to two decimal places
        findViewById<TextView>(R.id.text_product_price).text = "₱$formattedPrice"
    }
}