package com.example.githubuser.model.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.model.GithubUser
import com.example.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

	private val listGithubUser = MutableLiveData<List<GithubUser>>()
	val githubUser: LiveData<List<GithubUser>> = listGithubUser

	private val isLoading = MutableLiveData<Boolean>()
	val loading: LiveData<Boolean> = isLoading

	private val count = MutableLiveData<Int>()
	val totalCount: LiveData<Int> = count

	companion object {
		private const val TAG = "MainViewModel"
	}

	fun searchGithubUser(query: String) {
		isLoading.value = true
		ApiConfig.getApiService().searchUser(query).enqueue(object : Callback<UserResponse> {
			override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
				isLoading.value = false
				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						listGithubUser.value = responseBody.githubUsers
						count.value = responseBody.totalCount
					}
				} else {
					Log.e(TAG, "onFailure: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<UserResponse>, t: Throwable) {
				isLoading.value = false
				Log.e(TAG, "onFailure: ${t.message}")
			}
		})
	}
}