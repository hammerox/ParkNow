package com.mcustodio.parknow.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mcustodio.parknow.BuildConfig
import com.mcustodio.parknow.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        text_about_version.text = BuildConfig.VERSION_NAME
    }
}
