package com.cr7.bagpack.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cr7.bagpack.R
import com.cr7.bagpack.dataclasses.*

@TypeConverters(DateConverter::class)
@Database(entities = [TripDataClass::class,
    TripItemsDataClass::class], version = 1, exportSchema = true)
abstract class TripsDatabase : RoomDatabase(){

    abstract fun tripsDaoInterface(): TripsDaoInterface
    //static members and functions of the class
    companion object{
        private var tripsDatabase : TripsDatabase?= null

        fun getInstance(context: Context): TripsDatabase {
            if(tripsDatabase == null){
                tripsDatabase = Room.databaseBuilder(context,
                    TripsDatabase::class.java,
                    context.resources.getString(R.string.app_name))
                    .allowMainThreadQueries()
                    .build()
            }
            return tripsDatabase!!
        }
    }
}