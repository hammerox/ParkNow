package com.mcustodio.parknow.view.main.list


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.mcustodio.parknow.R
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.view.detail.DetailActivity
import com.mcustodio.parknow.view.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {


    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(MainViewModel::class.java) }
    private val adapter by lazy { ParkingRecyclerAdapter(onCardClick(), onCardLongClick()) }



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


    private fun onCardClick() : ((ParkingLot) -> Unit) = { parkingLot ->
        DetailActivity.launch(activity!!, parkingLot.id!!)
    }


    private fun onCardLongClick() : ((ParkingLot) -> Boolean) = { parkingLot ->
        AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.remove_description))
            .setPositiveButton(android.R.string.yes) { _, _ ->
                viewModel.delete(parkingLot)
                Toast.makeText(activity, "Removed", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton(android.R.string.no, null)
            .show()
        true
    }

}
