package com.example.githubuser.model.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.model.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

	private val listDetail = MutableLiveData<DetailResponse>()
	val detail: LiveData<DetailResponse> = listDetail

	private val isLoading = MutableLiveData<Boolean>()
	val loading: LiveData<Boolean> = isLoading

	companion object {
		private const val TAG = "UserDetailModel"
	}

	fun getGithubUser(login: String) {
		isLoading.value = true
		ApiConfig.getApiService().getUserDetail(login).enqueue(object : Callback<DetailResponse> {
			override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
				isLoading.value = false
				if (response.isSuccessful) {
					listDetail.value = response.body()
				} else {
					Log.e(TAG, "onFailure: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
				isLoading.value = false
				Log.e(TAG, "onFailure: ${t.message}")
			}
		})
	}
}