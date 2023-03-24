package com.example.githubuser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowerAdapter
import com.example.githubuser.model.GithubUser
import com.example.githubuser.ui.activity.UserDetailActivity
import com.example.githubuser.ui.viewmodel.FollowerViewModel
import com.example.githubuser.utility.Helper
import com.example.githubuser.databinding.FragmentFollowerBinding


class FollowerFragment : Fragment() {
	private var _binding: FragmentFollowerBinding? = null
	private val binding get() = _binding!!
	private lateinit var followerViewModel: FollowerViewModel
	private val helper = Helper()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
			FollowerViewModel::class.java)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentFollowerBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		followerViewModel.isLoading.observe(viewLifecycleOwner) {
			helper.showLoading(it, binding.progressFollower)
		}
		followerViewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
			setDataToFragment(listFollower)
		}

		followerViewModel.getFollower(arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString())
	}

	private fun setDataToFragment(listFollower: List<GithubUser>) {
		val listUser = ArrayList<GithubUser>()
		with(binding) {
			for (user in listFollower) {
				listUser.clear()
				listUser.addAll(listFollower)
			}
			rvFollower.layoutManager = LinearLayoutManager(context)
			val adapter = FollowerAdapter(listFollower)
			rvFollower.adapter = adapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}