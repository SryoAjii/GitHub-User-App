package com.example.fundafirstsub.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fundafirstsub.data.response.ItemsItem
import com.example.fundafirstsub.databinding.GitItemBinding

class UsersAdapter : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(item) }

    }

    class MyViewHolder(val binding: GitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.gitName.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.gitPicture)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}