package com.example.githubuser.api

import com.example.githubuser.model.DetailResponse
import com.example.githubuser.model.GithubUser
import com.example.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	@GET("search/users")
	fun searchUser(@Query("q") login: String): Call<UserResponse>

	@GET("users/{login}")
	fun getUserDetail(@Path("login") login: String): Call<DetailResponse>

	@GET("users/{login}/followers")
	fun getUserFollowers(@Path("login") login: String): Call<List<GithubUser>>

	@GET("users/{login}/following")
	fun getUserFollowings(@Path("login") login: String): Call<List<GithubUser>>
}