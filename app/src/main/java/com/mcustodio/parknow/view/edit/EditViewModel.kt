package com.mcustodio.parknow.view.edit

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mcustodio.parknow.model.ParkingLot
import com.mcustodio.parknow.repository.ParkingLotRepository

class EditViewModel(app: Application) : AndroidViewModel(app) {

    val parkingRepo = ParkingLotRepository()
    val insertSuccess = MutableLiveData<Boolean>()


    fun insert(parkingLot: ParkingLot) {
        parkingRepo.insert(parkingLot)
        insertSuccess.value = true
    }


}