package com.mcustodio.parknow

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        animation.reset()
        image_splash_logo.clearAnimation()
        image_splash_logo.startAnimation(animation)
        val splashScreenDuration = 2000L
        Handler().postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                splashScreenDuration
        )
    }
}