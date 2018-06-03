package com.mcustodio.parknow.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query


@Dao
interface ParkingLotDao : BaseDao<ParkingLot> {

    @Query("SELECT * FROM ParkingLot")
    fun getAll() : LiveData<List<ParkingLot>>

    @Query("SELECT * FROM ParkingLot")
    fun getAllList() : List<ParkingLot>

    @Query("DELETE FROM ParkingLot")
    fun deleteAll()

}