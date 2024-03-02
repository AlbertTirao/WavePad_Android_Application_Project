package com.example.wavepad

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView

class AccountPage : AppCompatActivity() {

    private val REQUEST_EDIT_PROFILE = 1
    private lateinit var profileImageView: CircleImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_page)

        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)

        profileImageView = findViewById(R.id.profileImageView)
        val editProfileButton: TextView = findViewById(R.id.editprofile)
        val logoutButton: TextView = findViewById(R.id.logout)

        editProfileButton.setOnClickListener {
            val intent = Intent(this@AccountPage, EditProfilePage::class.java)
            startActivityForResult(intent, REQUEST_EDIT_PROFILE)
        }

        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()

        val intent = Intent(this@AccountPage, LoginPage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
        }
    }
}
