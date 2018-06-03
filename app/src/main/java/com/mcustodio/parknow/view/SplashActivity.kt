package com.mcustodio.parknow.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import com.firebase.ui.auth.AuthUI
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mcustodio.parknow.R
import com.mcustodio.parknow.debug
import com.mcustodio.parknow.model.AppDatabase
import com.mcustodio.parknow.view.main.MainActivity


class SplashActivity : AppCompatActivity() {


    private val splashScreenDuration = 1000L
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
        val user = FirebaseAuth.getInstance().currentUser
        debug(user?.email)
        if (user != null) {
            login(user)
        } else {
            signIn()
        }
    }

    private fun signIn() {
        val providers = Arrays.asList(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val firebaseSignInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.FullScreen)
                .setAvailableProviders(providers)
                .build()

        startActivityForResult(firebaseSignInIntent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) {  // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                login(user!!)
            } else {
                finish()
            }
        }
    }

    private fun login(user: FirebaseUser) {
        AppDatabase.getFrom(this, user.uid)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}