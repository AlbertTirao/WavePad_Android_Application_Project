package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {

    private lateinit var edittextemail: EditText
    private lateinit var edittextpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        edittextemail = findViewById(R.id.editTextEmail)
        edittextpassword = findViewById(R.id.editTextPassword)

        val loginButton: Button = findViewById(R.id.loginButton)
        val textView: TextView = findViewById(R.id.Back)

        loginButton.setOnClickListener {
            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()

            //performLogin(email, password)
        }

        textView.setOnClickListener {
            onBackPressed()
        }
    }

//    private fun performLogin(email: String, password: String) {
//        val apiManager = ApiManager()
//        apiManager.login(email, password, object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.isSuccessful) {
//                    val loginResponse = response.body()
//                    if (loginResponse?.success == true) {
//                        // Login successful, navigate to the homepage
//                        val intent = Intent(this@LoginPage, HomePage::class.java)
//                        startActivity(intent)
//                        finish()
//                    } else {
//                        // Login failed, display error message or handle accordingly
//                        val message = loginResponse?.message ?: "Unknown error"
//                        Log.e("LoginPage", "Login failed: $message")
//                        // Display an error message to the user if needed
//                    }
//                } else {
//                    // Login failed due to server error
//                    val errorBody = response.errorBody()?.string()
//                    errorBody?.let { Log.e("LoginPage", it) }
//                    // Display an error message to the user if needed
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                // Login failed due to network issues or other errors
//                Log.e("LoginPage", "Failed to log in", t)
//                // Display an error message to the user if needed
//            }
//        })
//    }
}