package com.example.fundafirstsub.data.retrofit

import com.example.fundafirstsub.data.response.DetailUserResponse
import com.example.fundafirstsub.data.response.GithubResponse
import com.example.fundafirstsub.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGitUser (
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser (
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers (
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing (
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}