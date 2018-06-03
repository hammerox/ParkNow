package com.mcustodio.parknow

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import com.firebase.ui.auth.AuthUI
import java.util.*
import com.google.firebase.auth.FirebaseAuth



class SplashActivity : AppCompatActivity() {


    private val splashScreenDuration = 2000L
    private val requestCode = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animateLogo()
        Handler().postDelayed({ tryToLogin() }, splashScreenDuration)
    }

    private fun animateLogo() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        animation.reset()
        image_splash_logo.clearAnimation()
        image_splash_logo.startAnimation(animation)
    }

    private fun tryToLogin() {
        val providers = Arrays.asList(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val firebaseLoginIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.FullScreen)
                .setAvailableProviders(providers)
                .build()

        startActivityForResult(firebaseLoginIntent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) {  // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                finish()
            }
        }
    }
}