package com.dicoding.storyapp.presentation.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.databinding.ActivityDashboardBinding
import com.dicoding.storyapp.databinding.StoryCardItemBinding
import com.dicoding.storyapp.presentation.ui.camera.CameraActivity
import com.dicoding.storyapp.presentation.ui.shared.MarginItemDecoration
import com.dicoding.storyapp.presentation.ui.sign_in.SignInActivity
import com.dicoding.storyapp.presentation.ui.story_detail.StoryDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }
    private val viewModel: DashboardViewModel by viewModels()
    private val storiesAdapter = StoryAdapter(object : StoryAdapter.OnItemClickCallback {
        override fun onItemClicked(story: Story, itemBinding: StoryCardItemBinding) {
            goToDetailStoryActivity(story, itemBinding)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupActions()
    }

    override fun onResume() {
        super.onResume()

        val needToExit = intent.getBooleanExtra("EXIT", false)
        if (needToExit) {
            finish()
        }
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
                if (it.refresh is LoadState.Loading && !binding.shimmerFrame.isShimmerVisible) {
                    binding.swipeRefresh.isRefreshing = true
                } else if (it.refresh is LoadState.NotLoading) {
                    binding.rvStories.visibility = View.VISIBLE
                    binding.rvStories.scrollToPosition(0)
                    binding.shimmerView.visibility = View.GONE
                    binding.shimmerFrame.hideShimmer()
                    binding.swipeRefresh.isRefreshing = false
                } else if (it.refresh is LoadState.Error) {
                    binding.shimmerView.visibility = View.GONE
                    binding.shimmerFrame.hideShimmer()
                    binding.swipeRefresh.isRefreshing = false
                    (it.refresh as LoadState.Error).error.localizedMessage?.let { message ->
                        viewModel.postMessage(message)
                    }
                }
            }
        }

        lifecycleScope.launch {
            storiesAdapter.loadStateFlow.distinctUntilChangedBy { it.append }.collect {
                if (it.refresh is LoadState.Error) {
                    (it.refresh as LoadState.Error).error.localizedMessage?.let { message ->
                        viewModel.postMessage(message)
                    }
                }
            }
        }

        viewModel.message.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupActions() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout_action -> {
                    viewModel.logout()
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                    true
                }
                R.id.create_post_action -> {
                    startActivity(Intent(this, CameraActivity::class.java))
                    true
                }
                else -> false
            }
        }

        binding.swipeRefresh.setOnRefreshListener { storiesAdapter.refresh() }
    }

    private fun goToDetailStoryActivity(story: Story, itemBinding: StoryCardItemBinding) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            itemBinding.root.context as Activity,
            Pair(itemBinding.ivItemPhoto, getString(R.string.transition_photo)),
            Pair(itemBinding.tvItemName, getString(R.string.transition_name)),
            Pair(itemBinding.tvItemDescription, getString(R.string.transition_description)),
        )
        val intent = Intent(this, StoryDetailActivity::class.java)
        intent.putExtra(StoryDetailActivity.EXTRA_STORY, story)
        startActivity(intent, options.toBundle())
    }
}