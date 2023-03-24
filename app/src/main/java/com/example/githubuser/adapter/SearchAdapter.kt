package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.model.GithubUser

class SearchAdapter(private val listUser: List<GithubUser>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
	private lateinit var onItemClickCallback: OnItemClickCallback

	class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val user = listUser[position]

		with(holder.binding) {
			Glide.with(root.context).load(user.avatarUrl).circleCrop().into(profile)
			username.text = user.login
			url.text = user.htmlUrl
			root.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position]) }
		}
	}

	override fun getItemCount(): Int = listUser.size

	fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
		this.onItemClickCallback = onItemClickCallback
	}

	interface OnItemClickCallback {
		fun onItemClicked(data: GithubUser)
	}
}