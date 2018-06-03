package com.mcustodio.parknow

import android.util.Log

fun log(message: String?) {
    Log.d("AAA", message ?: "null")
}