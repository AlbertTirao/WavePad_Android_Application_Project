package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartPage : AppCompatActivity() {
    private val apiService: ApiService = RetrofitClient.instance
    private val userId = AuthManager.instance.getUserId() ?: -1 // Get the real user ID
    private lateinit var containerLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_page)

        containerLayout = findViewById(R.id.container_layout)

        fetchCartItems()

        val imageView: ImageView = findViewById(R.id.returnback)
        imageView.setOnClickListener {
            val signUpIntent = Intent(this@CartPage, HomePage::class.java)
            startActivity(signUpIntent)
        }
    }

    private fun fetchCartItems() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getCartItemsByUserId(userId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val cartsResponse = response.body()
                        val cartItems = cartsResponse?.carts
                        Log.d("CartPage", "Cart items: $cartItems") // Log response body
                        cartItems?.let {
                            displayCartItems(it)
                        } ?: run {
                            // Handle case when cart is empty
                            val emptyMessage = "Your cart is empty"
                            Toast.makeText(applicationContext, emptyMessage, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle failure
                        val errorMessage = "Failed fetch cart items: ${response.message()}"
                        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                        Log.e("CartPage", errorMessage)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("CartPage", "Error fetching cart items: ${e.message}")
                // Handle exception
                val errorMessage = "Error fetching cart items"
                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayCartItems(cartItems: List<CartItemResponse>) {
        // Clear any existing views in the container layout
        containerLayout.removeAllViews()

        // Iterate through each cart item and create a view for each one
        for (cartItem in cartItems) {
            val itemView = layoutInflater.inflate(R.layout.cart_item_layout, containerLayout, false)

            // Find views in the inflated layout
            val productTitle: TextView = itemView.findViewById(R.id.product_title)
            val productPrice: TextView = itemView.findViewById(R.id.product_price)
            val productSize: TextView = itemView.findViewById(R.id.product_categories)
            val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
            val productTotalPrice: TextView = itemView.findViewById(R.id.product_total_price)
            val deleteButton: Button = itemView.findViewById(R.id.delete_button)

            // Set data to the views
            productTitle.text = cartItem.product_name
            productPrice.text = "Price: $${cartItem.product_price}"
            productSize.text = "Size: ${cartItem.size}"
            productQuantity.text = "Quantity: ${cartItem.quantity}"
            productTotalPrice.text = "Total: $${cartItem.total}"

            // Set click listener for delete button
            deleteButton.setOnClickListener {
                deleteCartItem(cartItem.product_name)
            }

            // Add the inflated layout to the container layout
            containerLayout.addView(itemView)
        }
    }

    private fun deleteCartItem(productName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.deleteCartItem(userId, productName) // Modify the function call
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // Handle success
                        val successMessage = "Product removed from cart successfully"
                        showToast(successMessage)
                        Log.d("CartPage", successMessage)
                        // Refresh cart items after deletion
                        fetchCartItems()
                    } else {
                        // Handle failure
                        val errorMessage = "Failed to remove product from cart: ${response.message()}"
                        showToast(errorMessage)
                        Log.e("CartPage", errorMessage)
                    }
                }
            } catch (e: Exception) {
                // Handle exception
                val errorMessage = "Error deleting cart item: ${e.message}"
                showToast(errorMessage)
                Log.e("CartPage", errorMessage)
            }
        }
    }


    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        }
    }
}
