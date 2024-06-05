package com.example.fundafirstsub.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundafirstsub.databinding.ActivityFavBinding


class FavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavBinding
    private lateinit var adapter: FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.favList.layoutManager = layoutManager

        val favViewModel = getViewModel(this@FavActivity)
        favViewModel.getAllFav().observe(this) { favList ->
            if (favList != null) {
                adapter.setFav(favList)
            }
        }

        adapter = FavAdapter()
        binding.favList.setHasFixedSize(true)
        binding.favList.adapter = adapter

    }

    private fun getViewModel(activity: AppCompatActivity): FavViewModel {
        val modelFactory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, modelFactory)[FavViewModel::class.java]
    }
}