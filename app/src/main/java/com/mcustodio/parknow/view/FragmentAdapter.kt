package com.mcustodio.parknow.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentAdapter(fm: FragmentManager,
                      val listFragment: Fragment,
                      val mapFragment: Fragment) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> listFragment
            1 -> mapFragment
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}