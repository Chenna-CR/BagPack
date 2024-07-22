package com.cr7.bagpack.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cr7.bagpack.dataclasses.TripDataClass

@Dao
interface TripsDaoInterface {

    @Insert
    fun insertTripsDetails(tripDataClass: TripDataClass)

    @Query("SELECT * FROM TripDataClass")
    fun getTripsList() : List<TripDataClass>

    @Query("SELECT * FROM TripDataClass WHERE id= :id")
    fun getSingleTrip(id: Int) : TripDataClass

    @Update
    fun updateTripDetailsEntity(tripDataClass: TripDataClass)

    @Delete
    fun deleteTripDetails(tripDataClass: TripDataClass)


}