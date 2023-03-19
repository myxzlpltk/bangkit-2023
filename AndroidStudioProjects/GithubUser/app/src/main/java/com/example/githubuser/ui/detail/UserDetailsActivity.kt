package com.example.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.databinding.ActivityUserDetailsBinding
import com.example.githubuser.helper.ResultState
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }

    private val binding: ActivityUserDetailsBinding by lazy {
        ActivityUserDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel: UserDetailsViewModel by viewModels {
        UserDetailsViewModel.Factory.getInstance(this)
    }
    private val username: String by lazy { intent.getStringExtra(EXTRA_USER) as String }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Setup android header
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Observe data
        viewModel.getUser(username).observe(this) { result ->
            when (result) {
                is ResultState.Loading -> showLoading(true)
                is ResultState.Error -> {
                    if (viewModel.getUser(username).isInitialized) showLoading(false)
                    result.error.getContentIfNotHandled()?.let { text ->
                        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                    }
                }
                is ResultState.Success -> {
                    showLoading(false)
                    setUserData(result.data)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_share -> share()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_template, username))
        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.userDetailsContainer.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.userDetailsContainer.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUserData(user: UserEntity) {
        // Setup view pager
        binding.viewPager.adapter = SectionsPagerAdapter(this, user)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        // Update UI
        Glide.with(this).load(user.avatarUrl).into(binding.userAvatar)
        binding.userName.text = (user.name ?: "").ifEmpty { getString(R.string.no_name) }
        binding.userBio.text = (user.bio ?: "").ifEmpty { getString(R.string.no_bio) }
        binding.repoCount.text = user.publicRepos.toString()
        binding.followersCount.text = user.followers.toString()
        binding.followingCount.text = user.following.toString()
        binding.fabFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                binding.fabFavorite.context,
                if (user.isFavorite) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24
            )
        )

        // Set action
        binding.fabFavorite.setOnClickListener { viewModel.toggleFavorite(user) }
    }
}