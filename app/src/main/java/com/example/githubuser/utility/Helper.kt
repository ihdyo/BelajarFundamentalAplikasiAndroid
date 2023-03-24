package com.example.githubuser.utility

import android.view.View

class Helper {
	fun showLoading(isLoading: Boolean, view: View) {
		if (isLoading) {
			view.visibility = View.VISIBLE
		} else {
			view.visibility = View.INVISIBLE
		}
	}
}