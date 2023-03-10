package com.example.githubuser

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.adapters.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityUserDetailsBinding
import com.example.githubuser.networks.UserDetailsResponse
import com.example.githubuser.view_models.UserDetailsViewModel
import com.example.githubuser.view_models.UserDetailsViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Bind view */
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Setup view model */
        val username = (intent.getStringExtra(EXTRA_USER) as String).lowercase()
        viewModel = ViewModelProvider(
            this, UserDetailsViewModelFactory(username)
        )[UserDetailsViewModel::class.java]

        /* Setup android header */
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /* Observe data */
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.user.observe(this) { setUserData(it) }
        viewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { text ->
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    private fun setUserData(user: UserDetailsResponse) {
        /* Setup view pager */
        binding.viewPager.adapter = SectionsPagerAdapter(this, user)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        /* Update UI */
        Glide.with(this).load(user.avatarUrl).into(binding.userAvatar)
        binding.userName.text = if (user.name.isNullOrEmpty()) getString(R.string.no_name) else user.name
        binding.userBio.text = if (user.bio.isNullOrEmpty()) getString(R.string.no_bio) else user.bio
        binding.repoCount.text = user.publicRepos.toString()
        binding.followersCount.text = user.followers.toString()
        binding.followingCount.text = user.following.toString()
    }
}