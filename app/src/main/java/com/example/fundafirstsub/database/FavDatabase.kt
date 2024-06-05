package com.example.fundafirstsub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavEntity::class], version = 1)
abstract class FavDatabase: RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var INSTANCE: FavDatabase? = null

        @JvmStatic
        fun getData(context: Context): FavDatabase {
            if (INSTANCE == null) {
                synchronized(FavDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavDatabase::class.java, "fav_database")
                        .build()
                }
            }
            return INSTANCE as FavDatabase
        }
    }
}