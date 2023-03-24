package com.example.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowingAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.model.GithubUser
import com.example.githubuser.ui.activity.DetailActivity
import com.example.githubuser.model.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
	private lateinit var binding: FragmentFollowingBinding
	private lateinit var followingViewModel: FollowingViewModel

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentFollowingBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
		followingViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
			binding.progressFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
		}
		followingViewModel.following.observe(viewLifecycleOwner) { listFollowing ->
			setDataToFragment(listFollowing)
		}
		followingViewModel.getFollowing(arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString())
	}

	private fun setDataToFragment(listFollowing: List<GithubUser>) {
		binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
		binding.rvFollowing.adapter = FollowingAdapter(listFollowing)
	}
}