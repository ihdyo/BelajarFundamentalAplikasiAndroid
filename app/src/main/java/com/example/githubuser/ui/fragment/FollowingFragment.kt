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
import com.example.githubuser.ui.activity.UserDetailActivity
import com.example.githubuser.ui.viewmodel.FollowingViewModel
import com.example.githubuser.utility.Helper


class FollowingFragment : Fragment() {
	private var _binding: FragmentFollowingBinding? = null
	private val binding get() = _binding!!
	private lateinit var followingViewModel: FollowingViewModel
	private val helper = Helper()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
			FollowingViewModel::class.java)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentFollowingBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		followingViewModel.isLoading.observe(viewLifecycleOwner) {
			helper.showLoading(it, binding.progressFollowing)
		}
		followingViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
			setDataToFragment(listFollowing)
		}

		followingViewModel.getFollowing(arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString())
	}

	private fun setDataToFragment(listFollowing: List<GithubUser>) {
		val listUser = ArrayList<GithubUser>()
		with(binding) {
			for (user in listFollowing) {
				listUser.clear()
				listUser.addAll(listFollowing)
			}
			rvFollowing.layoutManager = LinearLayoutManager(context)
			val adapter = FollowingAdapter(listFollowing)
			rvFollowing.adapter = adapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}