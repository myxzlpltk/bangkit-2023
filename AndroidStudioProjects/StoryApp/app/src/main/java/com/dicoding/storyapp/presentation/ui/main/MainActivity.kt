package com.dicoding.storyapp.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.presentation.ui.dashboard.DashboardActivity
import com.dicoding.storyapp.presentation.ui.sign_in.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getUser().observe(this) { user ->
            val intent = if (user == null) {
                Intent(this, SignInActivity::class.java)
            } else {
                Intent(this, DashboardActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}