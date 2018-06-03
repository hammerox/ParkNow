package com.mcustodio.parknow

import android.app.Application
import com.mcustodio.parknow.model.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.getFrom(this)
    }
}