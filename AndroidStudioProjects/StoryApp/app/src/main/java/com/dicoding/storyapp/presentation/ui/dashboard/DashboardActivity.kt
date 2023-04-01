package com.dicoding.storyapp.presentation.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.ActivityDashboardBinding
import com.dicoding.storyapp.presentation.ui.main.MainViewModel
import com.dicoding.storyapp.presentation.ui.shared.MarginItemDecoration
import com.dicoding.storyapp.presentation.ui.sign_in.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }
    private val viewModel: DashboardViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val storiesAdapter = StoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupActions()
    }

    private fun setupView() {
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
            adapter = storiesAdapter
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                storiesAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            storiesAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }.collect {
                if (it.refresh is LoadState.Loading) {
                    binding.rvStories.visibility = View.GONE
                    binding.shimmerView.visibility = View.VISIBLE
                    binding.shimmerFrame.showShimmer(true)
                    binding.swipeRefresh.isRefreshing = true
                } else if (it.refresh is LoadState.NotLoading) {
                    binding.rvStories.visibility = View.VISIBLE
                    binding.shimmerView.visibility = View.GONE
                    binding.shimmerFrame.hideShimmer()
                    binding.swipeRefresh.isRefreshing = false
                    binding.rvStories.scrollToPosition(0)
                }
            }
        }
    }

    private fun setupActions() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.logout_action) {
                mainViewModel.logout()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
                true
            } else {
                false
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            storiesAdapter.refresh()
        }
    }
}