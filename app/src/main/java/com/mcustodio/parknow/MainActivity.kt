package com.mcustodio.parknow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.support.design.internal.BottomNavigationMenuView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resizeBottomNavIcons()
    }

    private fun resizeBottomNavIcons() {
        val menuView = nav_main_bottom.getChildAt(0) as BottomNavigationMenuView
        val size = 20f
        for (i in 0 until menuView.childCount) {
            val iconView = menuView.getChildAt(i).findViewById<View>(android.support.design.R.id.icon)
            val layoutParams = iconView.layoutParams
            val displayMetrics = resources.displayMetrics
            layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, displayMetrics).toInt()
            layoutParams.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, displayMetrics).toInt()
            iconView.layoutParams = layoutParams
        }
    }


}
