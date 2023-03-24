package com.example.githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.ViewPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.model.DetailResponse
import com.example.githubuser.model.GithubUser
import com.example.githubuser.model.viewmodel.UserDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityDetailBinding
	private val userDetailViewModel by viewModels<UserDetailViewModel>()


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val userIntent = intent.getParcelableExtra<GithubUser>(EXTRA_USER) as GithubUser

		userDetailViewModel.getGithubUser(userIntent.login)
		userDetailViewModel.detail.observe(this) { detailList ->
			setDataToView(detailList)
		}
		userDetailViewModel.loading.observe(this) { isLoading ->
			binding.progressDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
		}

		val login = Bundle().apply { putString(EXTRA_FRAGMENT, userIntent.login) }
		val sectionPagerAdapter = ViewPagerAdapter(this, login)
		binding.viewPager.adapter = sectionPagerAdapter

		val tabTitle = resources.getStringArray(R.array.tab_title)
		TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
			tab.text = tabTitle[position]
		}.attach()
	}

	private fun setDataToView(detailList: DetailResponse) {
		with(binding) {
			Glide.with(this@DetailActivity).load(detailList.avatarUrl).circleCrop().into(profile)
			name.text = detailList.name ?: "(unknown)"
			username.text = detailList.login
			bio.visibility = if (detailList.bio == null) View.GONE else View.VISIBLE
			bio.text = (detailList.bio ?: "") as CharSequence?
			followerCount.text = resources.getString(R.string.follower_count, detailList.followers)
			followingCount.text = resources.getString(R.string.following_count, detailList.following)
		}
	}

	companion object {
		const val EXTRA_USER = "extra_user"
		const val EXTRA_FRAGMENT = "extra_fragment"
	}
}
