package com.mcustodio.parknow.view.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import com.google.android.gms.maps.model.LatLng
import com.mcustodio.parknow.R
import com.mcustodio.parknow.hideWhenKeyboardIsVisible
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.switchVisibility
import kotlinx.android.synthetic.main.activity_edit.*

class DetailActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(DetailViewModel::class.java) }
    private val parkingId by lazy {
        val id = intent.getLongExtra(KEY_ID, -1L)
        if (id != -1L) id else null
    }
    private val viewOnly by lazy { intent.getBooleanExtra(KEY_VIEWONLY, false) }
    private val initialPosition by lazy { LatLng(intent.getDoubleExtra(KEY_LAT, 0.0), intent.getDoubleExtra(KEY_LNG, 0.0)) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setupView()
        setupButtons()
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        parkingId?.let { viewModel.getParkingLot(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }

    private fun setupView() {
        if (viewOnly == true) {
            input_detail_name.isEnabled = false
            input_detail_description.isEnabled = false
            input_detail_address.isEnabled = false
            input_detail_phone.isEnabled = false
            input_detail_openat.isEnabled = false
            input_detail_closeat.isEnabled = false
            input_detail_priceperhour.isEnabled = false
            input_detail_priceperday.isEnabled = false
            input_detail_pricepermonth.isEnabled = false
            fab_detail_edit.hideWhenKeyboardIsVisible(this)
        } else {
            fab_detail_done.hideWhenKeyboardIsVisible(this)
        }
    }

    private fun setupButtons() {
        fab_detail_edit.switchVisibility(viewOnly == true)
        fab_detail_edit.setOnClickListener { launchEdit(this, parkingId!!) }

        fab_detail_done.switchVisibility(viewOnly == false)
        fab_detail_done.setOnClickListener { upsertRecord() }

        image_detail_call.switchVisibility(viewOnly == true)
        image_detail_call.setOnClickListener {
            val phone = viewModel.parkingLot.value?.phone?.trim()
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        viewModel.parkingLot.observe(this, Observer {
            input_detail_name.setText(it?.name)
            input_detail_description.setText(it?.description)
            input_detail_address.setText(it?.address)
            input_detail_phone.setText(it?.phone)
            input_detail_openat.setText(it?.openAt?.toString())
            input_detail_closeat.setText(it?.closeAt?.toString())
            input_detail_priceperhour.setText(it?.pricePerHour?.toString())
            input_detail_priceperday.setText(it?.pricePerDay?.toString())
            input_detail_pricepermonth.setText(it?.pricePerMonth?.toString())
        })

        viewModel.actionSuccess.observe(this, Observer {
            if (it == true) finish()
        })
    }

    private fun upsertRecord() {
        val parkingLot = parkingId?.let { viewModel.parkingLot.value } ?: ParkingLot()
        parkingLot.name = input_detail_name.text.toString()
        parkingLot.description = input_detail_description.text.toString()
        parkingLot.address = input_detail_address.text.toString()
        parkingLot.phone = input_detail_phone.text.toString()
//            parkingLot.openAt = input_detail_openat
//            parkingLot.closeAt = input_detail_closeat
//            parkingLot.pricePerHour = input_detail_priceperhour.text.toString().toFloat()
//            parkingLot.pricePerDay = input_detail_priceperday.text.toString().toFloat()
//            parkingLot.pricePerMonth = input_detail_pricepermonth.text.toString().toFloat()
        parkingLot.latitude = initialPosition.latitude
        parkingLot.longitude = initialPosition.longitude
        viewModel.upsert(parkingLot)
    }



    companion object {
        const val KEY_ID = "KEY_ID"
        const val KEY_LAT = "KEY_LAT"
        const val KEY_LNG = "KEY_LNG"
        const val KEY_VIEWONLY = "KEY_VIEWONLY"

        fun launchViewOnly(context: Context, id: Long) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_ID, id)
            intent.putExtra(KEY_VIEWONLY, true)
            context.startActivity(intent)
        }

        fun launchNew(context: Context, pos: LatLng) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_LAT, pos.latitude)
            intent.putExtra(KEY_LNG, pos.longitude)
            context.startActivity(intent)
        }

        fun launchEdit(context: Context, id: Long) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_ID, id)
            context.startActivity(intent)
        }
    }
}
