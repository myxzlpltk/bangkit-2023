package com.dicoding.storyapp.presentation.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.ActivityDashboardBinding
import com.dicoding.storyapp.presentation.ui.dashboard.home.HomeFragment
import com.dicoding.storyapp.presentation.ui.dashboard.maps.MapsFragment
import com.dicoding.storyapp.presentation.ui.dashboard.profile.ProfileFragment
import com.dicoding.storyapp.presentation.ui.story_create.StoryCreateActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }

    private val launcherIntentPost = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            homeFragment.refreshAdapter()
        }
    }

    private val homeFragment = HomeFragment()
    private val mapsFragment = MapsFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.commit {
            replace(binding.navHostFragment.id, homeFragment, HOME_TAG)
        }
        setupActions()
    }

    private fun setupActions() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_action -> {
                    binding.postAction.show()
                    supportFragmentManager.commit {
                        replace(binding.navHostFragment.id, homeFragment, HOME_TAG)
                    }
                    true
                }
                R.id.maps_action -> {
                    binding.postAction.hide()
                    supportFragmentManager.commit {
                        replace(binding.navHostFragment.id, mapsFragment, MAPS_TAG)
                    }
                    true
                }
                R.id.profile_action -> {
                    binding.postAction.hide()
                    supportFragmentManager.commit {
                        replace(binding.navHostFragment.id, profileFragment, PROFILE_TAG)
                    }
                    true
                }
                else -> false
            }
        }

        binding.postAction.setOnClickListener {
            launcherIntentPost.launch(Intent(this, StoryCreateActivity::class.java))
        }
    }

    companion object {
        private const val HOME_TAG = "HomeFragment"
        private const val MAPS_TAG = "MapsFragment"
        private const val PROFILE_TAG = "ProfileFragment"
    }
}