package com.mcustodio.parknow

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import com.firebase.ui.auth.AuthUI
import android.widget.Toast
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavView()
    }

    private fun setupBottomNavView() {
        nav_main_bottom.setOnNavigationItemSelectedListener(onNavSelectedListener)
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

    private val onNavSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_main_list -> log("LISTA")
            R.id.action_main_map -> log("MAPA")
            R.id.action_main_more -> showMoreActions()
        }
        true
    }

    private val onMoreOptionListener = object : BottomSheetListener {
        override fun onSheetItemSelected(p0: BottomSheet, p1: MenuItem?, p2: Any?) {
            when (p1?.itemId) {
                R.id.action_main_logout -> askToLogout()
            }
            Toast.makeText(this@MainActivity, p1?.title, Toast.LENGTH_SHORT).show()
        }

        override fun onSheetDismissed(p0: BottomSheet, p1: Any?, p2: Int) {}
        override fun onSheetShown(p0: BottomSheet, p1: Any?) {}
    }

    private fun showMoreActions() {
        BottomSheet.Builder(this)
                .setSheet(R.menu.main_moreoptions)
                .setTitle("More options")
                .setListener(onMoreOptionListener)
//                .setObject(myObject)
                .show()
    }

    private fun askToLogout() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage(getString(R.string.main_logout))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                logout()
                Toast.makeText(this, "Yaay", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(android.R.string.no, null)
            .show()
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                finishAffinity()
                val intent = Intent(this, SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
    }


}
