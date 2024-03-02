package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProductFullDetail: AppCompatActivity() {
    private var quantity: Int = 1
    private lateinit var product: ProductDataClass

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

        quantity = intent.getIntExtra("QUANTITY", 1)

        product = intent.getSerializableExtra("PRODUCT") as ProductDataClass? ?: ProductDataClass()
        product?.let {
            productNameTextView.text = it.product_name
            productDescriptionTextView.text = it.description
            productImageView.setImageResource(it.product_image)
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