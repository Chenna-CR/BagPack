package com.cr7.bagpack.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class TripDataClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int= 0,
    var name: String?= null,
    var startingDate: Date?= Calendar.getInstance().time,
    var endingDate: Date?= Calendar.getInstance().time,
)
