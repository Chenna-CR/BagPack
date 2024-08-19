package com.cr7.bagpack.dataclasses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity(
    foreignKeys = [ForeignKey(
        parentColumns = ["id"],
        childColumns = ["tripId"],
        entity = TripDataClass::class
    )]
)
data class TripItemsDataClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var tripId: Int? = 0,
    var packingItem: String? = null,
    var isCompleted: Boolean? = false,
    var note: String? = null,
)
