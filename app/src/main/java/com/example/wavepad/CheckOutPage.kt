package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheckOutPage : AppCompatActivity() {

    private lateinit var Name: EditText
    private lateinit var editStreetAddress: EditText
    private lateinit var editTownCity: EditText
    private lateinit var editCountry: EditText
    private lateinit var editPostalZip: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var textProductName: TextView
    private lateinit var textQuantity: TextView
    private lateinit var textQuantityPrice: TextView
    private lateinit var buttonCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout)

        Name = findViewById(R.id.name)
        editStreetAddress = findViewById(R.id.edit_street_address)
        editTownCity = findViewById(R.id.edit_town_city)
        editCountry = findViewById(R.id.edit_country)
        editPostalZip = findViewById(R.id.edit_postal_zip)
        editEmail = findViewById(R.id.edit_email)
        editPhone = findViewById(R.id.edit_phone)
        textProductName = findViewById(R.id.text_product_name)
        textQuantity = findViewById(R.id.text_quantity)
        textQuantityPrice = findViewById(R.id.text_quantity_price)
        buttonCheckout = findViewById(R.id.button_checkout)

        val product = intent.getSerializableExtra("PRODUCT") as ProductDataClass?
        val productName = intent.getStringExtra("PRODUCT_NAME")
        val quantity = intent.getIntExtra("QUANTITY", 1)
        val productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)

        textProductName.text = productName
        textQuantity.text = "Quantity: $quantity"

        val totalPrice = productPrice * quantity * 2
        textQuantityPrice.text = "Total Price: ₱${String.format("%.2f", totalPrice)}"

        buttonCheckout.setOnClickListener {
            val name = Name.text.toString()
            val streetAddress = editStreetAddress.text.toString()
            val townCity = editTownCity.text.toString()
            val country = editCountry.text.toString()
            val postalZip = editPostalZip.text.toString()
            val email = editEmail.text.toString()
            val phone = editPhone.text.toString()

            if(name.isEmpty()){
                Name.error = "Name Required"
                Name.requestFocus()
                return@setOnClickListener
            }

            if(streetAddress.isEmpty()){
                editStreetAddress.error = "Street Address Required"
                editStreetAddress.requestFocus()
                return@setOnClickListener
            }

            if(townCity.isEmpty()){
                editTownCity.error = "Town/City Required"
                editTownCity.requestFocus()
                return@setOnClickListener
            }

            if(country.isEmpty()){
                editCountry.error = "Country Required"
                editCountry.requestFocus()
                return@setOnClickListener
            }

            if(postalZip.isEmpty()){
                editPostalZip.error = "Postal/Zip Required"
                editPostalZip.requestFocus()
                return@setOnClickListener
            }

            if(postalZip.length != 4){
                editPostalZip.error = "Postal/Zip must be 4 numbers"
                editPostalZip.requestFocus()
                return@setOnClickListener
            }

            if(email.isEmpty()){
                editEmail.error = "Email Required"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            if(phone.isEmpty()){
                editPhone.error = "Phone Number Required"
                editPhone.requestFocus()
                return@setOnClickListener
            }

            if(phone.length != 11){
                editPhone.error = "Phone Number must be 11 numbers"
                editPhone.requestFocus()
                return@setOnClickListener
            }
        }
    }
}
