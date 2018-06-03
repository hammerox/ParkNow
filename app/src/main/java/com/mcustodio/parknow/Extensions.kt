package com.mcustodio.parknow

import android.app.Activity
import android.graphics.Rect
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.View
import android.view.ViewGroup



fun debug(message: String?) {
    Log.d("AAA", message ?: "null")
}

fun Activity.setOnKeyboardListener(rootView: View? = null, onKeyboard: (isOpen: Boolean) -> Unit) {
    val view = rootView ?: this.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
    view.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        view.getWindowVisibleDisplayFrame(r)
        val screenHeight = view.rootView.height
        val keypadHeight = screenHeight - r.bottom
        onKeyboard.invoke(keypadHeight > screenHeight * 0.15)
    }
}

fun FloatingActionButton.hideWhenKeyboardIsVisible(activity: Activity) {
    activity.setOnKeyboardListener { isOpen ->
        when (isOpen) {
            true -> this.switchVisibility(false)
            false -> this.show()
        }
    }
}

fun View.switchVisibility(isVisible: Boolean? = null) {
    val show = isVisible ?: (this.visibility == View.GONE)
    this.visibility = if (show) View.VISIBLE else View.GONE
}
