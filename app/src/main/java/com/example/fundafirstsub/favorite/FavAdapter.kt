package com.example.fundafirstsub.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fundafirstsub.database.FavEntity
import com.example.fundafirstsub.databinding.GitItemBinding
import com.example.fundafirstsub.ui.GithubDetailActivity

class FavAdapter : RecyclerView.Adapter<FavAdapter.FavViewHolder>() {
    private val favList = ArrayList<FavEntity>()
    fun setFav(favList: List<FavEntity>) {
        val diffCallback = DiffCallback(this.favList, favList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.favList.clear()
        this.favList.addAll(favList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = GitItemBinding.inflate(LayoutInflater.from(parent.context),parent , false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(favList[position])
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    inner class FavViewHolder(private val binding: GitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fav: FavEntity) {
            with(binding) {
                gitName.text = fav.username
                Glide.with(binding.root.context)
                    .load(fav.avatarUrl)
                    .into(gitPicture)
                cardView.setOnClickListener{
                    val intent = Intent(it.context, GithubDetailActivity::class.java)
                    intent.putExtra( "EXTRA_NAME", fav.username)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}