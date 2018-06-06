package com.mcustodio.parknow.view.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import com.firebase.ui.auth.AuthUI
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener
import com.mcustodio.parknow.R
import com.mcustodio.parknow.model.AppDatabase
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.view.AboutActivity
import com.mcustodio.parknow.view.SplashActivity
import com.mcustodio.parknow.view.edit.EditActivity
import com.mcustodio.parknow.view.main.list.ListFragment
import com.mcustodio.parknow.view.main.map.MapFragment


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val pagerAdapter by lazy { FragmentAdapter(supportFragmentManager, ListFragment(), MapFragment()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavView()
        setupViewPagerAndFragments()
        setupViewModel()
    }

    private fun setupBottomNavView() {
        nav_main_bottom.setOnNavigationItemSelectedListener(onNavSelectedListener)
        // Diminuindo tamanho dos icons
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

    private fun setupViewPagerAndFragments() {
        viewpager_main.adapter = pagerAdapter
    }

    private fun setupViewModel() {
        viewModel.parkingLots.observe(this, Observer { data ->
            if (data == null || data.isEmpty()) {
                viewModel.add(ParkingLot.createMock())
            }
        })
    }

    private val onNavSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_main_list -> viewpager_main.currentItem = 0
            R.id.action_main_map -> viewpager_main.currentItem = 1
            R.id.action_main_more -> {
                showMoreActions()
                return@OnNavigationItemSelectedListener false
            }
        }
        true
    }

    private val onMoreOptionListener = object : BottomSheetListener {
        override fun onSheetItemSelected(p0: BottomSheet, p1: MenuItem?, p2: Any?) {
            when (p1?.itemId) {
                R.id.action_main_add -> EditActivity.launchNew(this@MainActivity)
                R.id.action_main_about -> startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                R.id.action_main_logout -> askToLogout()
            }
        }
        override fun onSheetDismissed(p0: BottomSheet, p1: Any?, p2: Int) {}
        override fun onSheetShown(p0: BottomSheet, p1: Any?) {}
    }

    private fun showMoreActions() {
        BottomSheet.Builder(this)
            .setSheet(R.menu.main_moreoptions)
            .setTitle("More options")
            .setListener(onMoreOptionListener)
            .show()
    }

    private fun askToLogout() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage(getString(R.string.main_logout))
            .setPositiveButton(android.R.string.yes) { _, _ -> logout() }
            .setNegativeButton(android.R.string.no, null)
            .show()
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                AppDatabase.firstAccess = true  // Logout from Database
                finishAffinity()
                val intent = Intent(this, SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
    }


}
