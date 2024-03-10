package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ProductFullDetail: AppCompatActivity() {
    private var quantity: Int = 1
    private lateinit var product: ProductDataClass
    private var isBuyNowClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_full_detail)

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