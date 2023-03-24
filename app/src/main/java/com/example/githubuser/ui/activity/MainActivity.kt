package com.example.githubuser.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.SearchAdapter
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.model.GithubUser
import com.example.githubuser.ui.viewmodel.MainViewModel
import com.example.githubuser.utility.Helper

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private val mainViewModel by viewModels<MainViewModel>()
	private var listGithubUser = ArrayList<GithubUser>()
	private val helper = Helper()

	override fun onCreate(savedInstanceState: Bundle?) {
		binding = ActivityMainBinding.inflate(layoutInflater)
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		mainViewModel.listGithubUser.observe(this) { listGithubUser ->
			setUserData(listGithubUser)
		}

		mainViewModel.isLoading.observe(this) {
			helper.showLoading(it, binding.progressMain)
		}

		mainViewModel.totalCount.observe(this) {
			showText(it)
		}

		val layoutManager = LinearLayoutManager(this@MainActivity)
		binding.rvUser.layoutManager = layoutManager
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.main_menu, menu)

		val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
		val searchView = menu!!.findItem(R.id.search).actionView as SearchView

		searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
		searchView.queryHint = resources.getString(R.string.hint)
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				query?.let {
					binding.rvUser.visibility = View.VISIBLE
					mainViewModel.searchGithubUser(it)
					setUserData(listGithubUser)
				}
				hideKeyboard()
				return true
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				return false
			}
		})
		return true
	}

	private fun setUserData(listGithubUser: List<GithubUser>) {
		val listUser = ArrayList<GithubUser>()
		for (user in listGithubUser) {
			listUser.clear()
			listUser.addAll(listGithubUser)
		}
		val adapter = SearchAdapter(listUser)
		binding.rvUser.adapter = adapter

		adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
			override fun onItemClicked(data: GithubUser) {
				showSelectedUser(data)
			}
		})
	}

	private fun showSelectedUser(data: GithubUser) {
		val moveWithParcelableIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
		moveWithParcelableIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
		startActivity(moveWithParcelableIntent)
	}

	private fun showText(totalCount: Int) {
		if (totalCount == 0) {
			binding.hintMain.visibility = View.VISIBLE
			binding.arrow.visibility = View.VISIBLE
			Toast.makeText(binding.root.context, R.string.empty, Toast.LENGTH_SHORT).show()
		} else {
			binding.hintMain.visibility = View.GONE
			binding.arrow.visibility = View.GONE
		}
	}

	private fun hideKeyboard() {
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(binding.rvUser.windowToken, 0)
	}
}