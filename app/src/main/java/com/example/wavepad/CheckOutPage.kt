package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheckOutPage : AppCompatActivity() {

    private lateinit var editFirstName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editStreetAddress: EditText
    private lateinit var editTownCity: EditText
    private lateinit var editCountry: EditText
    private lateinit var editPostalZip: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var textProductName: TextView
    private lateinit var textQuantity: TextView
    private lateinit var buttonCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout)

        editFirstName = findViewById(R.id.edit_first_name)
        editLastName = findViewById(R.id.edit_last_name)
        editStreetAddress = findViewById(R.id.edit_street_address)
        editTownCity = findViewById(R.id.edit_town_city)
        editCountry = findViewById(R.id.edit_country)
        editPostalZip = findViewById(R.id.edit_postal_zip)
        editEmail = findViewById(R.id.edit_email)
        editPhone = findViewById(R.id.edit_phone)
        textProductName = findViewById(R.id.text_product_name)
        textQuantity = findViewById(R.id.text_quantity)
        buttonCheckout = findViewById(R.id.button_checkout)

        val productName = intent.getStringExtra("PRODUCT_NAME")
        val quantity = intent.getIntExtra("QUANTITY", 1)

        textProductName.text = productName
        textQuantity.text = "Quantity: $quantity"

        buttonCheckout.setOnClickListener {
            // Implement checkout functionality here
        }
    }
}
