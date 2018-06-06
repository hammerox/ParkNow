package com.mcustodio.parknow.view.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.repository.ParkingLotRepository

class MainViewModel(app: Application) : AndroidViewModel(app) {


    private val parkingRepo = ParkingLotRepository()
    val parkingLots: LiveData<List<ParkingLot>>
    val isCreationMode = MutableLiveData<Boolean>()

    init {
        parkingLots = parkingRepo.queryAll()
        isCreationMode.value = false
    }


    fun add(parkingLot: ParkingLot) {
        parkingRepo.insert(parkingLot)
    }


    fun delete(parkingLot: ParkingLot) {
        parkingRepo.delete(parkingLot)
    }
}