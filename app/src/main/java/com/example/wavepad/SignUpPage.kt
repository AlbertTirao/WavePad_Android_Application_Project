package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPage : AppCompatActivity() {

    private lateinit var emailedittext: EditText
    private lateinit var usernameeditext: EditText
    private lateinit var passwordedittext: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        emailedittext = findViewById(R.id.editTextEmail)
        usernameeditext = findViewById(R.id.editTextName)
        passwordedittext = findViewById(R.id.editTextPassword)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val textView: TextView = findViewById(R.id.Login)

        signUpButton.setOnClickListener {
            val name = emailedittext.text.toString().trim()
            val email = emailedittext.text.toString().trim()
            val username = usernameeditext.text.toString().trim()
            val password = passwordedittext.text.toString().trim()
            val mobilephone = "09054788094"
            val age = 0
            val gender = "male"

//            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || mobilephone.isEmpty() || age == 0 || gender.isEmpty()) {
//                // Handle empty fields or any validation logic here
//                return@setOnClickListener
//            }
            RetrofitClient.instance.registerUser(name, "email1@gmail.com", "12345678", 12345678901, "Male", 21,0).enqueue(object :
                Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignUpPage, response.body()?.message, Toast.LENGTH_LONG).show()
                        val signUpIntent = Intent(this@SignUpPage, HomePage::class.java)
                        startActivity(signUpIntent)
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                        Toast.makeText(this@SignUpPage, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpPage, "Email Already Exist", Toast.LENGTH_LONG).show()
                }
            })
        }

        textView.setOnClickListener {
            val signUpIntent = Intent(this@SignUpPage, LoginPage::class.java)
            startActivity(signUpIntent)
        }
    }

}
