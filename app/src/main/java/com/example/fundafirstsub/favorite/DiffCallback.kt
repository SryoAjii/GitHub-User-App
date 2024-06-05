package com.example.fundafirstsub.favorite

import androidx.recyclerview.widget.DiffUtil
import com.example.fundafirstsub.database.FavEntity

class DiffCallback(private val oldFavList: List<FavEntity>, private val newFavList: List<FavEntity>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].username == newFavList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav.username == newFav.username
    }
}