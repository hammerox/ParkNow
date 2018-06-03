package com.mcustodio.parknow

import android.util.Log

fun debug(message: String?) {
    Log.d("AAA", message ?: "null")
}