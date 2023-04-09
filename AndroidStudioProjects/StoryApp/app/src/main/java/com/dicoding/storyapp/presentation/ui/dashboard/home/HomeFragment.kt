package com.dicoding.storyapp.presentation.ui.dashboard.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.databinding.FragmentHomeBinding
import com.dicoding.storyapp.databinding.StoryCardItemBinding
import com.dicoding.storyapp.presentation.ui.dashboard.StoryAdapter
import com.dicoding.storyapp.presentation.ui.shared.MarginItemDecoration
import com.dicoding.storyapp.presentation.ui.story_detail.StoryDetailActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import androidx.core.util.Pair as UtilPair

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    private val storiesAdapter = StoryAdapter(object : StoryAdapter.OnItemClickCallback {
        override fun onItemClicked(story: Story, itemBinding: StoryCardItemBinding) {
            goToDetailStoryActivity(story, itemBinding)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            viewModel.stories.collectLatest { pagingData ->
                storiesAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            storiesAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }.collect {
                    binding.rvStories.visibility = View.VISIBLE
                    binding.swipeRefresh.isRefreshing = false
                    binding.shimmerView.visibility = View.GONE
                    binding.shimmerFrame.hideShimmer()
                    delay(300)
                    binding.rvStories.scrollToPosition(0)
                }
        }

        lifecycleScope.launch {
            storiesAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.Error }.collect {
                    binding.shimmerView.visibility = View.GONE
                    binding.shimmerFrame.hideShimmer()
                    binding.swipeRefresh.isRefreshing = false
                    (it.refresh as LoadState.Error).error.localizedMessage?.let { message ->
                        viewModel.postMessage(message)
                    }
                }
        }

        viewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupActions() {
        binding.swipeRefresh.setOnRefreshListener { storiesAdapter.refresh() }
    }

    fun refreshAdapter() = storiesAdapter.refresh()

    private fun goToDetailStoryActivity(story: Story, itemBinding: StoryCardItemBinding) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            itemBinding.root.context as Activity,
            UtilPair(itemBinding.ivItemPhoto, getString(R.string.transition_photo)),
            UtilPair(itemBinding.tvItemName, getString(R.string.transition_name)),
            UtilPair(itemBinding.tvItemDescription, getString(R.string.transition_description)),
        )
        val intent = Intent(activity, StoryDetailActivity::class.java)
        intent.putExtra(StoryDetailActivity.EXTRA_STORY, story)
        startActivity(intent, options.toBundle())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}