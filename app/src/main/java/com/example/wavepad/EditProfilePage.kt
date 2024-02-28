package com.example.wavepad

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class EditProfilePage : AppCompatActivity() {

    private val REQUEST_IMAGE_PICK = 100
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_page)

        val saveButton: Button = findViewById(R.id.saveButton)
        val changeImageButton: Button = findViewById(R.id.changeImageButton)
        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val contactInfoEditText: EditText = findViewById(R.id.contactInfoEditText)
        val profileImageView: ImageView = findViewById(R.id.profileImageView)

        changeImageButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val contactInfo = contactInfoEditText.text.toString()

            val intent = Intent()
            intent.putExtra("name", name)
            intent.putExtra("contactInfo", contactInfo)
            selectedImageUri?.let { intent.putExtra("profileImageUri", it.toString()) }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            val profileImageView: ImageView = findViewById(R.id.profileImageView)
            profileImageView.setImageURI(selectedImageUri)
        }
    }
}
