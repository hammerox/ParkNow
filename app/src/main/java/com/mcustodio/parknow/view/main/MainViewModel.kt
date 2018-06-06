package com.mcustodio.parknow.view.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.repository.ParkingLotRepository

class MainViewModel(app: Application) : AndroidViewModel(app) {


    private val parkingRepo = ParkingLotRepository()
    val parkingLots: LiveData<List<ParkingLot>>

    init {
        parkingLots = parkingRepo.queryAll()
    }


    fun add(parkingLot: ParkingLot) {
        parkingRepo.insert(parkingLot)
    }


    fun delete(parkingLot: ParkingLot) {
        parkingRepo.delete(parkingLot)
    }
}