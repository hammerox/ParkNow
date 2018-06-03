package com.mcustodio.parknow.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
class ParkingLot(@PrimaryKey(autoGenerate = true) var id : Long? = null,
                 var date: Date? = null,
                 var description: String? = null) {

}