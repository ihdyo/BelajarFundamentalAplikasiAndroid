package com.example.githubuser.model.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

	private val listFollower = MutableLiveData<List<GithubUser>>()
	val follower: LiveData<List<GithubUser>> = listFollower

	private val isLoading = MutableLiveData<Boolean>()
	val loading: LiveData<Boolean> = isLoading

	companion object {
		private const val TAG = "FollowerViewModel"
	}

	fun getFollower(username: String) {
		isLoading.value = true
		ApiConfig.getApiService().getUserFollowers(username).enqueue(object : Callback<List<GithubUser>> {
			override fun onResponse(
				call: Call<List<GithubUser>>,
				response: Response<List<GithubUser>>
			) { isLoading.value = false
				if (response.isSuccessful) {
					listFollower.value = response.body()
				} else {
					Log.e(TAG, "onFailure: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
				isLoading.value = false
				Log.e(TAG, "onFailure: ${t.message}")
			}
		})
	}
}