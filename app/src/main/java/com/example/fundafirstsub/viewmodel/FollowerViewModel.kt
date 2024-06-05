package com.example.fundafirstsub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundafirstsub.data.response.ItemsItem
import com.example.fundafirstsub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    private val _follower = MutableLiveData<List<ItemsItem>>()
    val follower: LiveData<List<ItemsItem>> = _follower

    private val _followerLoading = MutableLiveData<Boolean>()
    val followerLoading: LiveData<Boolean> = _followerLoading

    fun getFollower(find: String) {
        _followerLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(find)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _followerLoading.value = false
                if (response.isSuccessful) {
                    _follower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _followerLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowerList"
    }
}