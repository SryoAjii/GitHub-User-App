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

class FollowingViewModel : ViewModel() {

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _followingLoading = MutableLiveData<Boolean>()
    val followingLoading: LiveData<Boolean> = _followingLoading

    fun getFollowing(find: String) {
        _followingLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(find)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _followingLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _followingLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowingList"
    }

}