package com.example.githubuser.model.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

	private val listFollowing = MutableLiveData<List<GithubUser>>()
	val following: LiveData<List<GithubUser>> = listFollowing

	private val isLoading = MutableLiveData<Boolean>()
	val loading: LiveData<Boolean> = isLoading

	companion object {
		private const val TAG = "FollowingViewModel"
	}

	fun getFollowing(username: String) {
		isLoading.value = true
		ApiConfig.getApiService().getUserFollowings(username).enqueue(object : Callback<List<GithubUser>> {
			override fun onResponse(
				call: Call<List<GithubUser>>,
				response: Response<List<GithubUser>>
			) { isLoading.value = false
				if (response.isSuccessful) {
					listFollowing.value = response.body()
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