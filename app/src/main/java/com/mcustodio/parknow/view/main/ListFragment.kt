package com.mcustodio.parknow.view.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mcustodio.parknow.R
import com.mcustodio.parknow.model.ParkingLot
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val adapter by lazy { ParkingRecyclerAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        view.recycler_mainlist.layoutManager = LinearLayoutManager(activity)
        view.recycler_mainlist.adapter = adapter
        adapter.recordList = arrayListOf(ParkingLot.createMock())
        return view
    }




}
