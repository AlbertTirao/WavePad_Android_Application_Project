package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var logo: ImageView
    private lateinit var welcomeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        logo = findViewById(R.id.logosplash)
        welcomeText = findViewById(R.id.welcometext)

        val fadeInLogoText = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeInLogoText.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                navigateToNextActivity()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        logo.startAnimation(fadeInLogoText)
        welcomeText.startAnimation(fadeInLogoText)
    }

    private fun navigateToNextActivity() {
        val intent = Intent(this@SplashScreen, GetStartedPage::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
