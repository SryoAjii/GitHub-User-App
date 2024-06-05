package com.example.fundafirstsub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundafirstsub.data.response.GithubResponse
import com.example.fundafirstsub.data.response.ItemsItem
import com.example.fundafirstsub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _gitUsers = MutableLiveData<List<ItemsItem>>()
    val gitUsers: LiveData<List<ItemsItem>> = _gitUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findGithub()
    }

    fun findGithub(find: String = "a") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGitUser(find)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _gitUsers.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}