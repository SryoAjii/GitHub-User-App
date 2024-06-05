package com.example.fundafirstsub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fundafirstsub.data.response.DetailUserResponse
import com.example.fundafirstsub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _gitDetail = MutableLiveData<DetailUserResponse>()
    val gitDetail: LiveData<DetailUserResponse> = _gitDetail

    private val _detailLoading = MutableLiveData<Boolean>()
    val detailLoading: LiveData<Boolean> = _detailLoading


    fun getDetail(find: String) {
        _detailLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(find)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _detailLoading.value = false
                if (response.isSuccessful) {
                    _gitDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _detailLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "GithubDetailAcitivity"
    }

}