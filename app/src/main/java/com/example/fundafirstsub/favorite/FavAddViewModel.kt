package com.example.fundafirstsub.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fundafirstsub.data.repository.FavRepository
import com.example.fundafirstsub.database.FavEntity

class FavAddViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)

    fun insert(favEntity: FavEntity) {
        mFavRepository.insert(favEntity)
    }

    fun delete(favEntity: FavEntity) {
        mFavRepository.delete(favEntity)
    }

    fun getUsername(login :String): LiveData<FavEntity> {
        return mFavRepository.getUsername(login)
    }
}