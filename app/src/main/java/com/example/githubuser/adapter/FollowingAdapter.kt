package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.model.GithubUser
import com.example.githubuser.databinding.ItemUserBinding

class FollowingAdapter(private val listFollowing: List<GithubUser>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){
	class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val following = listFollowing[position]

		with(holder.binding) {
			com.bumptech.glide.Glide.with(root.context).load(following.avatarUrl).circleCrop().into(profile)
			username.text = following.login
			url.text = following.htmlUrl
		}
	}

	override fun getItemCount(): Int = listFollowing.size
}