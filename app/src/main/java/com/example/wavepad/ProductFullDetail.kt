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
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductFullDetail: AppCompatActivity() {
    private var quantity: Int = 1
    private lateinit var product: ProductDataClass
    private var isBuyNowClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_full_detail)
        val apiService = RetrofitClient.instance

        val returnBackButton:ImageButton = findViewById(R.id.returnback)
        val addToCartButton: ImageButton = findViewById(R.id.AddToCart)
        val buyNowButton: Button = findViewById(R.id.BuyNow)
        val quantityTextView: TextView = findViewById(R.id.text_quantity)
        val minusButton: ImageButton = findViewById(R.id.btn_minus)
        val plusButton: ImageButton = findViewById(R.id.btn_plus)
        val productNameTextView: TextView = findViewById(R.id.text_product_name)
        val productDescriptionTextView: TextView = findViewById(R.id.descriptiontext)
        val productCategoryTextView: TextView = findViewById(R.id.category)
        val productImageView: ImageView = findViewById(R.id.image_product)

        quantity = intent.getIntExtra("QUANTITY", 1)

        product = intent.getSerializableExtra("PRODUCT") as ProductDataClass? ?: ProductDataClass()
        product?.let {
            productNameTextView.text = it.product_name
            productDescriptionTextView.text = it.description
            productCategoryTextView.text = "Category: ${it.category_id}"
            updatePrice()

            val authorTextView: TextView = findViewById(R.id.text_author)
            it.author?.let { author ->
                authorTextView.text = "Author: ${author.name}"
            }

            val imageUrl = "https://wavepad-ecom-529a3cf49f8f.herokuapp.com/front/images/product_images/large/${it.product_image}"
            Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.baseline_error_24)
                .into(productImageView)
        }

        quantityTextView.text = "$quantity"

        returnBackButton.setOnClickListener {
            val signUpIntent = Intent(this@ProductFullDetail, HomePage::class.java)
            startActivity(signUpIntent)
        }

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
            val userId = AuthManager.instance.getUserId() ?: -1
            val productId = product.id // Replace with actual product ID//5 inaro ni bert, 6 ocakes
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
            if (!isBuyNowClicked) {
                val checkoutIntent = Intent(this@ProductFullDetail, CheckOutPage::class.java).apply {
                    putExtra("PRODUCT", product)
                    putExtra("QUANTITY", quantity)
                    putExtra("PRICE", product.product_price.toDouble())
                }
                startActivity(checkoutIntent)
                isBuyNowClicked = false
            }
        }
    }
    private fun updatePrice() {
        val totalPrice = product?.product_price?.toDouble() ?: 0.0 * quantity
        val formattedPrice = String.format("%.2f", totalPrice)
        findViewById<TextView>(R.id.text_product_price).text = "₱$formattedPrice"
    }
}