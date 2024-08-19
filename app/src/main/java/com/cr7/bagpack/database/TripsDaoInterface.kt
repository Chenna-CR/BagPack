package com.cr7.bagpack.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cr7.bagpack.dataclasses.TripDataClass
import com.cr7.bagpack.dataclasses.TripItemsDataClass
import java.util.Date

@Dao
interface TripsDaoInterface {

    @Insert
    fun insertTripsDetails(tripDataClass: TripDataClass)

    @Query("SELECT * FROM TripDataClass")
    fun getTripsList() : List<TripDataClass>

    @Query("SELECT * FROM TripDataClass where startingDate>=:startDate and startingDate<=:endDate")
    fun getTripsListDateWise(startDate: Long, endDate: Long) : List<TripDataClass>

    @Query("SELECT * FROM TripDataClass WHERE id= :id")
    fun getSingleTrip(id: Int) : TripDataClass

    @Update
    fun updateTripDetailsEntity(tripDataClass: TripDataClass)

    @Delete
    fun deleteTripDetails(tripDataClass: TripDataClass)

    //trip items crud

    @Insert
    fun insertPackingList(tripItemsDataClass: TripItemsDataClass)

    @Query("SELECT * FROM TripItemsDataClass where tripId=:tripId")
    fun getTripItemsList(tripId: Int) : List<TripItemsDataClass>

    @Update
    fun updateTripPackingItems(tripItemsDataClass: TripItemsDataClass)

    @Delete
    fun deletePackingItems(tripItemsDataClass: TripItemsDataClass)


}