package com.mcustodio.parknow.view.detail

import android.app.Application
import android.arch.lifecycle.*
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.repository.ParkingLotRepository

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private val parkingRepo = ParkingLotRepository()
    val parkingLot = MediatorLiveData<ParkingLot>()
    val actionSuccess = MutableLiveData<Boolean>()


    fun getParkingLot(id: Long) {
        parkingLot.addSource(parkingRepo.queryById(id)) { parkingLot.value = it }

    }


    fun upsert(parkingLot: ParkingLot) {
        if (parkingLot.recordId == null) parkingRepo.insert(parkingLot)
        else parkingRepo.update(parkingLot)
        actionSuccess.value = true
    }


}