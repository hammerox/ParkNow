package com.mcustodio.parknow.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.mcustodio.parknow.model.AppDatabase
import com.mcustodio.parknow.model.ParkingLot
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ParkingLotRepository {

    private val database = AppDatabase.getInstance().parkingLotDao()
    var onQuerySuccess: ((ParkingLot?) -> Unit)? = null

    fun queryAll() : LiveData<List<ParkingLot>> {
        return database.getAll()
    }

    fun queryById(id: Long) {
        Observable.just(database)
                .doOnNext {
                    onQuerySuccess?.invoke(database.getById(id))
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
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