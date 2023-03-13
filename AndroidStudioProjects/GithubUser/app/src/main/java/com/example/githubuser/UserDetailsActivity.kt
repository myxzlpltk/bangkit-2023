package com.example.githubuser

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityUserDetailsBinding
import com.example.githubuser.networks.UserDetailsResponse
import com.example.githubuser.view_models.UserDetailsViewModel

class UserDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityUserDetailsBinding
    private val viewModel by viewModels<UserDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Bind view */
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Get extra data */
        val username = intent.getStringExtra(EXTRA_USER) as String
        viewModel.getUser(username)

        /* Setup android header */
        supportActionBar?.title = username.lowercase()
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
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.userDetailsContainer.visibility = View.VISIBLE
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setUserData(user: UserDetailsResponse) {
        Glide.with(this).load(user.avatarUrl).into(binding.userAvatar)
        binding.userName.text = if (user.name.isNullOrEmpty()) "No Name" else user.name
        binding.userBio.text = if (user.bio.isNullOrEmpty()) "No Bio" else user.bio
        binding.repoCount.text = user.publicRepos.toString()
        binding.followersCount.text = user.followers.toString()
        binding.followingCount.text = user.following.toString()
    }
}