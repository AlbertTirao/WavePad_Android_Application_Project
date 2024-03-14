package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPage : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var mobilephone: EditText
    private lateinit var password: EditText
    private lateinit var gender: EditText
    private lateinit var age: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        name = findViewById(R.id.editTextName)
        email = findViewById(R.id.editTextEmail)
        mobilephone = findViewById(R.id.editTextMobilePhone)
        password = findViewById(R.id.editTextPassword)
        gender = findViewById(R.id.editTextGender)
        age = findViewById(R.id.editTextAge)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val textView: TextView = findViewById(R.id.Login)

        signUpButton.setOnClickListener {
            val inputName = name.text.toString().trim()
            val inputEmail = email.text.toString().trim()
            val inputPassword = password.text.toString().trim()
            val inputMobilePhone = mobilephone.text.toString().trim()
            val inputGender = gender.text.toString().trim()
            val inputAgeText = age.text.toString().trim()

            if(inputName.isEmpty()){
                name.error = "Nickname Required"
                name.requestFocus()
                return@setOnClickListener
            }
            if(inputName.length !in 6..99){
                name.error = "Nickname Must Be 8 Character Long"
                name.requestFocus()
                return@setOnClickListener
            }
            if(inputEmail.isEmpty()){
                email.error = "Email Required"
                email.requestFocus()
                return@setOnClickListener
            }
            if(inputPassword.isEmpty()){
                password.error = "Password Required"
                password.requestFocus()
                return@setOnClickListener
            }
            if(inputPassword.length !in 6..99){
                password.error = "Password Must Be 8 Character Long"
                password.requestFocus()
                return@setOnClickListener
            }
            if(inputMobilePhone.isEmpty()){
                mobilephone.error = "Mobile Phone Required Required"
                mobilephone.requestFocus()
                return@setOnClickListener
            }
            if(inputMobilePhone.length != 11){
                mobilephone.error = "Phone Number must be 11 numbers"
                mobilephone.requestFocus()
                return@setOnClickListener
            }
            if(inputGender.isEmpty()){
                gender.error = "Gender Required Required"
                gender.requestFocus()
                return@setOnClickListener
            }
            if(inputAgeText.isEmpty()){
                age.error = "Age Required"
                age.requestFocus()
                return@setOnClickListener
            }
            if(inputAgeText.length != 2){
                age.error = "Age Must have Two Number"
                age.requestFocus()
                return@setOnClickListener
            }

            val inputAge = inputAgeText.toInt()

            RetrofitClient.instance.registerUser(inputName, inputEmail, inputPassword, inputMobilePhone, inputGender, inputAge, status = 0)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@SignUpPage, response.body()?.message, Toast.LENGTH_LONG).show()
                            val signUpIntent = Intent(this@SignUpPage, HomePage::class.java)
                            signUpIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(signUpIntent)
                            overridePendingTransition(R.anim.slide_in_left, R.anim.scale_down)
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
            val loginIntent = Intent(this@SignUpPage, LoginPage::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(loginIntent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.scale_down)
        }
    }
}
