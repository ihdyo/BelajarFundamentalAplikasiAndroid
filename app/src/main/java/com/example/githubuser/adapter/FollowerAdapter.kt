package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.model.GithubUser
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemUserBinding

class FollowerAdapter(private val listFollower: List<GithubUser>) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>(){
	class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val follower = listFollower[position]

		with(holder.binding) {
			Glide.with(root.context).load(follower.avatarUrl).circleCrop().into(profile)
			username.text = follower.login
			url.text = follower.htmlUrl
		}
	}

	override fun getItemCount(): Int = listFollower.size
}