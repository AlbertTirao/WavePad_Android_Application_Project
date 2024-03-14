package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrdersSuccessPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_success_page)

        val messageTextView: TextView = findViewById(R.id.messageTextView)
        val viewOrdersButton: Button = findViewById(R.id.viewOrdersButton)
        val returnHomeButton: Button = findViewById(R.id.returnHomeButton)

        messageTextView.text = "Your order has been placed!"

        viewOrdersButton.setOnClickListener {
            startActivity(Intent(this@OrdersSuccessPage, OrdersPage::class.java))
            finish() // Optional: finish the orders success page activity
        }

        returnHomeButton.setOnClickListener {
            startActivity(Intent(this@OrdersSuccessPage, HomePage::class.java))
            finish() // Optional: finish the orders success page activity
        }
    }
}