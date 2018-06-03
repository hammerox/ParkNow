package com.mcustodio.parknow.model

import android.arch.persistence.room.TypeConverter
import java.sql.Time
import java.util.*


class Converter {

    val DELIMITER = "/*/"

    @TypeConverter
    fun toDate(value: Long?): Date? = value?.let { Date(value) }

    @TypeConverter
    fun toLong(value: Date?): Long? = value?.time

    @TypeConverter
    fun arrayToString(value: Array<String>?) : String? = value?.joinToString(DELIMITER)

    @TypeConverter
    fun stringToArray(value: String?) : Array<String>? = value?.split(DELIMITER)?.toTypedArray()

    @TypeConverter
    fun timeToString(value: Time?) : String? = value?.toString()

    @TypeConverter
    fun stringToTime(value: String?) : Time? = value?.let { Time.valueOf(it) }

}