package com.example.fundafirstsub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FavEntity)

    @Delete
    fun delete(entity: FavEntity)

    @Query("SELECT * FROM FAVENTITY ORDER BY USERNAME ASC")
    fun getAllEntity(): LiveData<List<FavEntity>>

    @Query("SELECT * FROM FAVENTITY WHERE USERNAME = :login")
    fun getUsername(login: String): LiveData<FavEntity>
}