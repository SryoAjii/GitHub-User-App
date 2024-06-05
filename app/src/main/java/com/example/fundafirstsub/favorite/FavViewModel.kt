package com.example.fundafirstsub.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fundafirstsub.data.repository.FavRepository
import com.example.fundafirstsub.database.FavEntity

class FavViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)

    fun getAllFav(): LiveData<List<FavEntity>> = mFavRepository.getAllFav()
}