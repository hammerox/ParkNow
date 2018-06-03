package com.mcustodio.parknow.view.main


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mcustodio.parknow.R
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(MainViewModel::class.java) }
    private val adapter by lazy { ParkingRecyclerAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        view.recycler_mainlist.layoutManager = LinearLayoutManager(activity)
        view.recycler_mainlist.adapter = adapter

        viewModel.parkingLots.observe(this, Observer { data ->
            adapter.recordList = data ?: arrayListOf()
        })

        return view
    }




}
