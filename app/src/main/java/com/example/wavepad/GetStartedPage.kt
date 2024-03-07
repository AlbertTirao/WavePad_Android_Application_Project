package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GetStartedPage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.getstarted_page)

        val getStarted: Button = findViewById(R.id.GettedStart)

        getStarted.setOnClickListener {
            val signUpIntent = Intent(this@GetStartedPage, LoginPage::class.java)
            signUpIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(signUpIntent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.scale_down)

            animateButton(getStarted)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.scale_up, 0)
    }

    private fun animateButton(button: Button) {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation)
        button.startAnimation(animation)
    }
}