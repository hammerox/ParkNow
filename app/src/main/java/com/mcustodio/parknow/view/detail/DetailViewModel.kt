package com.mcustodio.parknow.view.detail

import android.app.Application
import android.arch.lifecycle.*
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.repository.ParkingLotRepository

class DetailViewModel(app: Application) : AndroidViewModel(app) {

    private val parkingRepo = ParkingLotRepository()
    val parkingLot: MutableLiveData<ParkingLot> = MutableLiveData()
    val actionSuccess = MutableLiveData<Boolean>()


    init {
        parkingRepo.onQuerySuccess = {
            parkingLot.postValue(it)
        }
    }


    fun getParkingLot(id: Long) {
        parkingRepo.queryById(id)
    }


    fun upsert(parkingLot: ParkingLot) {
        if (parkingLot.recordId == null) parkingRepo.insert(parkingLot)
        else parkingRepo.update(parkingLot)
        actionSuccess.value = true
    }


}