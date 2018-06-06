package com.mcustodio.parknow.view.main.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mcustodio.parknow.view.main.MainViewModel

class MapFragment : SupportMapFragment() {


    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(MainViewModel::class.java) }
    private var map: GoogleMap? = null



    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync { map ->
            this.map = map
            setupViewModel()
        }
    }

    private fun setupViewModel() {
        viewModel.parkingLots.observe(this, Observer { data ->
            data?.let { map?.clear() }
            data?.map {
                val pos = LatLng(it.latitude!!, it.longitude!!)
                MarkerOptions()
                        .position(pos)
                        .title(it.name)
            }?.forEach { map?.addMarker(it) }
        })
    }
}