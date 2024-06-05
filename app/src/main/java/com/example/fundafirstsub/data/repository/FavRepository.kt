package com.example.fundafirstsub.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.fundafirstsub.database.FavDao
import com.example.fundafirstsub.database.FavDatabase
import com.example.fundafirstsub.database.FavEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {
    private val mFavDao: FavDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavDatabase.getData(application)
        mFavDao = db.favDao()
    }

    fun getAllFav(): LiveData<List<FavEntity>> = mFavDao.getAllEntity()

    fun getUsername(login: String): LiveData<FavEntity> {
        return mFavDao.getUsername(login)
    }

    fun insert(favEntity: FavEntity) {
        executorService.execute { mFavDao.insert(favEntity) }
    }

    fun delete(favEntity: FavEntity) {
        executorService.execute { mFavDao.delete(favEntity) }
    }

}