package com.mcustodio.parknow.view.main.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mcustodio.parknow.R
import com.mcustodio.parknow.model.ParkingLot

class ParkingRecyclerAdapter(val onClick: ((ParkingLot) -> Unit),
                             val onLongClick: ((ParkingLot) -> Boolean)) : RecyclerView.Adapter<ParkingViewHolder>() {


    var recordList: List<ParkingLot> = ArrayList()
        set (list) {
            field = list
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parking, parent, false)
        return ParkingViewHolder(view, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: ParkingViewHolder, position: Int) {
        val parkingLot = recordList[position]
        holder.setValues(parkingLot)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }
}