package com.example.wavepad

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import java.util.*

class GetStartedPage: AppCompatActivity() {

    private lateinit var imageViewPager: ViewPager
    private lateinit var adapter: ImagePagerAdapter
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.getstarted_page)

        imageViewPager = findViewById(R.id.imageViewPager)
        adapter = ImagePagerAdapter(this)
        imageViewPager.adapter = adapter

        // Start auto sliding
        startAutoSlide()

        val getStarted: Button = findViewById(R.id.GettedStart)

        getStarted.setOnClickListener {
            val signUpIntent = Intent(this@GetStartedPage, LoginPage::class.java)
            signUpIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(signUpIntent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.scale_down)

            animateButton(getStarted)
        }

        // Slow down the slide animation
        // Remove the page.scaleY property in the PageTransformer
        imageViewPager.setPageTransformer(true) { page, position ->
            page.translationX = -position * page.width
            page.alpha = 1 - Math.abs(position)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.scale_up, 0)
    }

    private fun animateButton(button: Button) {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation)
        animation.repeatCount = Animation.INFINITE // Set animation to repeat infinitely
        button.startAnimation(animation)
    }

    // Start auto sliding
    private fun startAutoSlide() {
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    if (imageViewPager.currentItem < adapter.count - 1) {
                        imageViewPager.currentItem = imageViewPager.currentItem + 1
                    } else {
                        imageViewPager.currentItem = 0
                    }
                }
            }
        }, 3000, 5000) // Change these values for slide duration and delay
    }
}
