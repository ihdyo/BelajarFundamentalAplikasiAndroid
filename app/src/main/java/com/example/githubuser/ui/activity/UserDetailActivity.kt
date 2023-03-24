package com.example.githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.model.DetailResponse
import com.example.githubuser.model.GithubUser
import com.example.githubuser.ui.viewmodel.UserDetailViewModel
import com.example.githubuser.utility.Helper
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityUserDetailBinding
	private val userDetailViewModel by viewModels<UserDetailViewModel>()
	private val helper = Helper()

	companion object {
		const val EXTRA_USER = "extra_user"
		const val EXTRA_FRAGMENT = "extra_fragment"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		binding = ActivityUserDetailBinding.inflate(layoutInflater)
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		userDetailViewModel.listDetail.observe(this) { detailList ->
			setDataToView(detailList)
		}

		userDetailViewModel.isLoading.observe(this) {
			helper.showLoading(it, binding.progressDetail)
		}

		setTabLayoutView()
	}

	private fun setTabLayoutView() {
		val userIntent = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser
		userDetailViewModel.getGithubUser(userIntent.login)

		val login = Bundle()
		login.putString(EXTRA_FRAGMENT, userIntent.login)

		val sectionPagerAdapter = SectionsPagerAdapter(this, login)
		val viewPager: ViewPager2 = binding.viewPager

		viewPager.adapter = sectionPagerAdapter
		val tabs: TabLayout = binding.tabs
		val tabTitle = resources.getStringArray(R.array.tab_title)
		TabLayoutMediator(tabs, viewPager) { tab, position ->
			tab.text = tabTitle[position]
		}.attach()
	}

	private fun setDataToView(detailList: DetailResponse) {
		binding.apply {
			Glide.with(this@UserDetailActivity).load(detailList.avatarUrl).circleCrop().into(profile)
			name.text = detailList.name ?: "(unknown)"
			username.text = detailList.login
			if (detailList.bio == null) {
				bio.visibility = View.GONE
			} else {
				bio.text = detailList.bio.toString()
			}
			followerCount.text = resources.getString(R.string.follower_count, detailList.followers)
			followingCount.text = resources.getString(R.string.following_count, detailList.following)
		}
	}
}