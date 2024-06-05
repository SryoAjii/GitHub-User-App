package com.example.fundafirstsub.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.fundafirstsub.R
import com.example.fundafirstsub.data.response.DetailUserResponse
import com.example.fundafirstsub.database.FavEntity
import com.example.fundafirstsub.databinding.ActivityGithubDetailBinding
import com.example.fundafirstsub.favorite.FavAddViewModel
import com.example.fundafirstsub.favorite.ViewModelFactory
import com.example.fundafirstsub.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GithubDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGithubDetailBinding

    private lateinit var favAddViewModel: FavAddViewModel

    private var fav: FavEntity? = null
    private var isFav: LiveData<FavEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGithubDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.gitDetail.observe(this) { gitDetail ->
            setUserData(gitDetail)
        }

        detailViewModel.detailLoading.observe(this) {
            detailLoading(it)
        }

        val login = intent.getStringExtra("EXTRA_NAME").toString()
        val avatarUrl = intent.getStringExtra("EXTRA_IMG").toString()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tablayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.getDetail(login)
        sectionsPagerAdapter.username = login

        favAddViewModel = getViewModel(this@GithubDetailActivity)

        fav = FavEntity(login, avatarUrl)
        isFav = favAddViewModel.getUsername(login)

        isFav?.observe(this) {
            if (isFav?.value != null){
                binding.fabButton.setImageDrawable(getDrawable(R.drawable.ic_favorite))
            } else {
                binding.fabButton.setImageDrawable(getDrawable(R.drawable.ic_favorite_border))
            }
        }

        binding.fabButton.setOnClickListener {
            if (isFav?.value != null) {
                favAddViewModel.delete(fav as FavEntity)
            } else {
                favAddViewModel.insert(fav as FavEntity)
            }
        }
    }

    private fun setUserData(user: DetailUserResponse) {
        binding.name.text = user.name
        binding.username.text = user.login
        binding.followers.text = "${user.followers} Followers"
        binding.following.text = "${user.following} Following"
        Glide.with(binding.root.context)
            .load(user.avatarUrl)
            .into(binding.detailPicture)
    }

    private fun detailLoading(state: Boolean) {
        binding.detailProgressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun getViewModel(activity: AppCompatActivity): FavAddViewModel {
        val modelFactory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, modelFactory)[FavAddViewModel::class.java]
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}