package com.example.githubuser.ui.activity

import android.app.SearchManager
import android.content.Context
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
import com.example.githubuser.model.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private val mainViewModel by viewModels<MainViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.rvUser.layoutManager = LinearLayoutManager(this)

		mainViewModel.githubUser.observe(this) { githubUser ->
			binding.rvUser.adapter = SearchAdapter(githubUser)
		}
		mainViewModel.loading.observe(this) { isLoading ->
			binding.progressMain.visibility = if (isLoading) View.VISIBLE else View.GONE
		}
		mainViewModel.totalCount.observe(this) { totalCount ->
			if (totalCount == 0) {
				binding.hintMain.visibility = View.VISIBLE
				binding.arrow.visibility = View.VISIBLE
				Toast.makeText(binding.root.context, R.string.empty, Toast.LENGTH_SHORT).show()
			} else {
				binding.hintMain.visibility = View.GONE
				binding.arrow.visibility = View.GONE
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.main_menu, menu)

		val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
		val searchView = menu!!.findItem(R.id.search).actionView as SearchView

		searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
		searchView.queryHint = resources.getString(R.string.hint)
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				query?.let {
					mainViewModel.searchGithubUser(it)
				}
				val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
				imm.hideSoftInputFromWindow(binding.rvUser.windowToken, 0)
				return true
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				return false
			}
		})
		return true
	}
}