package com.mcustodio.parknow.view.main.list

import android.support.v7.widget.RecyclerView
import android.view.View
import com.mcustodio.parknow.model.ParkingLot
import kotlinx.android.synthetic.main.item_parking.view.*

class ParkingViewHolder(view: View,
                        val onClick: ((ParkingLot) -> Unit),
                        val onLongClick: ((ParkingLot) -> Boolean)
) : RecyclerView.ViewHolder(view) {

    private val card = view.card_itempark
    private val name = view.text_itempark_name
    private val address = view.text_itempark_address
    private val pricePerHour = view.text_itempark_priceperhour
    private val pricePerDay = view.text_itempark_priceperday
    private val pricePerMonth = view.text_itempark_pricepermonth


    fun setValues(parkingLot: ParkingLot) {
        name.text = parkingLot.name
        address.text = parkingLot.address
        pricePerHour.text = parkingLot.pricePerHour.toString()
        pricePerDay.text = parkingLot.pricePerDay.toString()
        pricePerMonth.text = parkingLot.pricePerMonth.toString()

        card.setOnClickListener { onClick(parkingLot) }
        card.setOnLongClickListener { onLongClick(parkingLot) }
    }
}