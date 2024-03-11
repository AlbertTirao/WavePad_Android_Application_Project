package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

            if(email.isEmpty()){
                edittextemail.error = "Email Required"
                edittextemail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                edittextpassword.error = "Password Required"
                edittextpassword.requestFocus()
                return@setOnClickListener
            }
            if(password.length !in 8..99){
                edittextpassword.error = "Password Must Be 8 Character Long"
                edittextpassword.requestFocus()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.Main) {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.loginUser(email, password).enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if (response.isSuccessful) {
                                val token = response.body()?.token
                                val userId = response.body()?.user?.id
                                if (userId != null) {
                                    Log.d("MyApp", "User ID: $userId")
                                } else {
                                    Log.d("MyApp", "User ID is null or empty")
                                }
                                if (userId != null) {
                                    AuthManager.instance.setUserId(userId.toInt())
                                }
                                Toast.makeText(this@LoginPage, response.body()?.message, Toast.LENGTH_LONG).show()
                                if (token != null) {
                                    AuthManager.instance.setAuthToken(token)
                                }
                                val signUpIntent = Intent(this@LoginPage, HomePage::class.java)
                                startActivity(signUpIntent)
                                overridePendingTransition(R.anim.slide_in_left, R.anim.scale_down)
                            } else {
                                Toast.makeText(this@LoginPage, "Incorrect Password", Toast.LENGTH_LONG).show()
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
        signUpIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(signUpIntent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.scale_down)
    }
}
