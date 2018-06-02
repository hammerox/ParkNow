package com.mcustodio.parknow

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getFrom(this)
    }
}