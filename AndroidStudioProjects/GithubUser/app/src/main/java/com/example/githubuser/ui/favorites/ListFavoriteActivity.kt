package com.example.githubuser.ui.favorites

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.databinding.ActivityListFavoriteBinding
import com.example.githubuser.helper.ResultState

class ListFavoriteActivity : AppCompatActivity() {

    private val binding: ActivityListFavoriteBinding by lazy {
        ActivityListFavoriteBinding.inflate(layoutInflater)
    }
    private val viewModel: ListFavoriteViewModel by viewModels {
        ListFavoriteViewModel.Factory.getInstance(this)
    }
    private val adapter = ListUserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Setup action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup recycler view
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        // Observe live data
        viewModel.getFavoriteUsers().observe(this) { result ->
            when (result) {
                is ResultState.Loading -> showLoading(true)
                is ResultState.Success -> {
                    // Remove loading
                    showLoading(false)
                    // Show empty list if is exists
                    showEmptyResult(result.data.isEmpty())
                    // Update update
                    adapter.setListUser(result.data)
                }
                is ResultState.Error -> {
                    showLoading(false)
                    result.error.getContentIfNotHandled()?.let { text ->
                        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showEmptyResult(isEmpty: Boolean) {
        if (isEmpty) {
            binding.emptyView.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.rvUsers.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.rvUsers.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}