package com.mcustodio.parknow.view.main.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mcustodio.parknow.view.main.MainViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.mcustodio.parknow.debug
import com.mcustodio.parknow.view.edit.EditActivity


class MapFragment : SupportMapFragment() {


    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(MainViewModel::class.java) }
    private var map: GoogleMap? = null
    private var pins: List<MarkerOptions>? = null
    private var movablePin: Marker? = null



    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync { map ->
            this.map = map
            this.map?.setOnMarkerDragListener(onDragListener)
            this.map?.uiSettings?.isZoomControlsEnabled = true
            setupViewModel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pins = null     // Para não vazar memória
    }

    private fun setupViewModel() {
        viewModel.parkingLots.observe(this, Observer { data ->
            pins = data?.map {
                val pos = LatLng(it.latitude!!, it.longitude!!)
                MarkerOptions()
                        .position(pos)
                        .title(it.name)
            }
            showParkingLots()
        })

        viewModel.isCreationMode.observe(this, Observer {
            it?.let {
                if (it == true) {
                    map?.clear()
                    map?.setOnMapClickListener { moveMarker(it)}
                    map?.setOnMapLongClickListener { moveMarker(it) }
                    map?.setOnMarkerClickListener { true }
                    movablePin = map?.addMarker(movableMarker(LatLng(0.0,0.0)))
                } else {
                    movablePin = null
                    showParkingLots()
                }
            }
        })
    }

    private fun showParkingLots() {
        map?.clear()
        pins?.forEach { map?.addMarker(it) }
    }

    private fun moveMarker(pos: LatLng) {
        movablePin?.position = pos
        map?.animateCamera(CameraUpdateFactory.newLatLng(pos))
    }

    private fun movableMarker(pos: LatLng) : MarkerOptions {
        return MarkerOptions()
                .position(pos)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
    }

    fun launchEditActivity() {
        movablePin?.position?.let { EditActivity.launchNew(activity!!, it) }
    }

    private val onDragListener = object : GoogleMap.OnMarkerDragListener {
        override fun onMarkerDragStart(p0: Marker?) {}
        override fun onMarkerDrag(p0: Marker?) {}
        override fun onMarkerDragEnd(p0: Marker?) {
            map?.animateCamera(CameraUpdateFactory.newLatLng(p0?.position))
        }
    }
}