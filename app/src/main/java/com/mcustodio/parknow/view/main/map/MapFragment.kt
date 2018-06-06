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
import com.mcustodio.parknow.view.edit.EditActivity
import com.google.android.gms.maps.model.LatLngBounds




class MapFragment : SupportMapFragment() {


    private val viewModel by lazy { ViewModelProviders.of(activity!!).get(MainViewModel::class.java) }
    private var map: GoogleMap? = null
    private var pins: List<MarkerOptions>? = null
    private var movablePin: Marker? = null



    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        getMapAsync { map ->
            this.map = map
            this.map?.uiSettings?.isZoomControlsEnabled = true
            setupViewModel()
            map?.setOnMarkerClickListener { viewModel.isCreationMode.value == true }    // false = usa comportamento default
            map?.setOnCameraMoveListener { movablePin?.position = this@MapFragment.map?.cameraPosition?.target }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pins = null     // Evitar memory leak
    }

    private fun setupViewModel() {
        viewModel.parkingLots.observe(this, Observer { data ->
            pins = data?.map {
                val pos = LatLng(it.latitude!!, it.longitude!!)
                MarkerOptions()
                    .position(pos)
                    .title(it.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            }
            showParkingLots()
        })

        viewModel.isCreationMode.observe(this, Observer {
            if (it == true) {
                map?.clear()
                movablePin = map?.addMarker(MarkerOptions().position(map!!.cameraPosition.target))
            } else {
                movablePin = null
                showParkingLots()
            }
        })
    }

    private fun showParkingLots() {
        map?.clear()
        val markers = pins?.map { map?.addMarker(it) }  // Add markers to map and save to variable
        val builder = LatLngBounds.Builder()
        if (markers?.isNotEmpty() == true) {
            markers.filterNotNull().forEach { builder.include(it.position) }
            val bounds = builder.build()
            val camera = CameraUpdateFactory.newLatLngBounds(bounds, 250)
            map?.animateCamera(camera)
        }
    }

    fun launchEditActivity() {
        movablePin?.position?.let { EditActivity.launchNew(activity!!, it) }
        viewModel.isCreationMode.value = false
    }
}