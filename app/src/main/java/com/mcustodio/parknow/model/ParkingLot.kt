package com.mcustodio.parknow.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.sql.Time
import java.util.*


@Entity
class ParkingLot(@PrimaryKey(autoGenerate = true) var id : Long? = null,
                 var createdDate: Date? = null,
                 var name: String? = null,
                 var description: String? = null,
                 var address: String? = null,
                 var phone: String? = null,
                 var openAt: Time? = null,
                 var closeAt: Time? = null,
                 var pricePerHour: Float? = null,
                 var pricePerDay: Float? = null,
                 var pricePerMonth: Float? = null,
                 var latitude: Double? = null,
                 var longitude: Double? = null
                 ) {


    companion object {
        fun createMock() : ParkingLot {
            val mock = ParkingLot()
            mock.apply {
                createdDate = Calendar.getInstance().time
                name = "FIAP"
                description = "Estacionamento próximo da FIAP"
                address = "Av. Paulista, 1.106 - Bela Vista, São Paulo - SP, 01310-100"
                phone = "019992256303"
                openAt = Time.valueOf("08:00:00")
                closeAt = Time.valueOf("22:00:00")
                pricePerHour = 10.0f
                pricePerDay = 30.0f
                pricePerMonth = 300.0f
                latitude = -23.564296
                longitude = -46.652622
            }
            return mock
        }
    }

}