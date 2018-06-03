package com.mcustodio.parknow.repository

import android.arch.lifecycle.LiveData
import com.mcustodio.parknow.model.AppDatabase
import com.mcustodio.parknow.model.ParkingLot
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ParkingLotRepository {

    private val database = AppDatabase.getInstance().parkingLotDao()

    fun queryAll() : LiveData<List<ParkingLot>> {
        return database.getAll()
    }

    fun insert(parkingLot: ParkingLot) {
        Observable.just(database)
                .doOnNext { it.insert(parkingLot) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun update(parkingLot: ParkingLot) {
        Observable.just(database)
                .doOnNext { it.update(parkingLot) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateAll(parkingLots: List<ParkingLot>) {
        Observable.just(database)
                .doOnNext { it.update(parkingLots) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun delete(parkingLot: ParkingLot) {
        Observable.just(database)
                .doOnNext { it.delete(parkingLot) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun clearAll() {
        Observable.just(database)
                .doOnNext { it.deleteAll() }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}