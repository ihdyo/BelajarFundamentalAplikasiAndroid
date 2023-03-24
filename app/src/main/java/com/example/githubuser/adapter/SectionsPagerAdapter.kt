package com.example.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.ui.fragment.FollowerFragment
import com.example.githubuser.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: Bundle) : FragmentStateAdapter(activity) {
	override fun getItemCount(): Int {
		return 2
	}

	override fun createFragment(position: Int): Fragment {
		var fragment: Fragment? = null
		when (position) {
			0 -> fragment = FollowerFragment()
			1 -> fragment = FollowingFragment()
		}
		fragment?.arguments = login
		return fragment as Fragment
	}
}