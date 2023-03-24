package com.example.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowerAdapter
import com.example.githubuser.databinding.FragmentFollowerBinding
import com.example.githubuser.model.GithubUser
import com.example.githubuser.ui.activity.DetailActivity
import com.example.githubuser.model.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {
	private lateinit var binding: FragmentFollowerBinding
	private lateinit var followerViewModel: FollowerViewModel

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentFollowerBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		followerViewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)
		followerViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
			binding.progressFollower.visibility = if (isLoading) View.VISIBLE else View.GONE
		}
		followerViewModel.follower.observe(viewLifecycleOwner) { listFollower ->
			setDataToFragment(listFollower)
		}
		followerViewModel.getFollower(arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString())
	}

	private fun setDataToFragment(listFollower: List<GithubUser>) {
		binding.rvFollower.layoutManager = LinearLayoutManager(requireContext())
		binding.rvFollower.adapter = FollowerAdapter(listFollower)
	}
}