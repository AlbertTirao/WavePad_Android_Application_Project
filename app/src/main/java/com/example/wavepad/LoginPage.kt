package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        val signUpTextView: TextView = findViewById(R.id.SignUp)

        loginButton.setOnClickListener {
            val email = edittextemail.text.toString().trim()
            val password = edittextpassword.text.toString().trim()

            GlobalScope.launch(Dispatchers.Main) {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.loginUser(email, password).enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if (response.isSuccessful) {
                                val token = response.body()?.token
                                val userId = response.body()?.data?.id
                                if (userId != null) {
                                    AuthManager.instance.setUserId(userId.toInt())
                                }
                                Toast.makeText(this@LoginPage, response.body()?.message, Toast.LENGTH_LONG).show()
                                if (token != null) {
                                    AuthManager.instance.setAuthToken(token)
                                }
                                val signUpIntent = Intent(this@LoginPage, HomePage::class.java)
                                startActivity(signUpIntent)

                            } else {
                                Toast.makeText(this@LoginPage, "Invalid Credentials", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            // Handle failure, e.g., show an error message
                            Toast.makeText(this@LoginPage, "Login failed. Please try again.", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }
        signUpTextView.setOnClickListener {
            navigateToSignUp()
        }
    }
    private fun navigateToSignUp() {
        val signUpIntent = Intent(this, SignUpPage::class.java)
        startActivity(signUpIntent)
    }
}
