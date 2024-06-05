package com.example.fundafirstsub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundafirstsub.R
import com.example.fundafirstsub.data.response.ItemsItem
import com.example.fundafirstsub.databinding.ActivityMainBinding
import com.example.fundafirstsub.favorite.FavActivity
import com.example.fundafirstsub.preferences.PreferenceActivity
import com.example.fundafirstsub.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.gitList.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.gitUsers.observe(this) { gitUsers ->
            setGitUser(gitUsers)
        }

        mainViewModel.isLoading.observe(this) {
            loading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val search = searchView.text
                    searchBar.hint = search
                    mainViewModel.findGithub(search.toString())
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        binding.searchBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.favMenu -> {
                    val favIntent = Intent(this@MainActivity, FavActivity::class.java)
                    startActivity(favIntent)
                    true
                }
                R.id.prefMenu -> {
                    val prefIntent = Intent(this@MainActivity, PreferenceActivity::class.java)
                    startActivity(prefIntent)
                    true
                } else -> false
            }
        }

    }

    private fun loading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setGitUser(githubUser: List<ItemsItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(githubUser)
        binding.gitList.adapter = adapter

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val moveWithDataIntent = Intent(this@MainActivity, GithubDetailActivity::class.java)
                moveWithDataIntent.putExtra("EXTRA_NAME", data.login)
                moveWithDataIntent.putExtra("EXTRA_IMG", data.avatarUrl)
                startActivity(moveWithDataIntent)
            }
        })
    }

}