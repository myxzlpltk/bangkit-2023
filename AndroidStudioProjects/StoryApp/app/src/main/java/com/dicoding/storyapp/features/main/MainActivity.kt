package com.dicoding.storyapp.features.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.features.sign_in.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getUser().observe(this) { user ->
            if (user == null) {
                startActivity(Intent(this@MainActivity, SignInActivity::class.java))
                finish()
            } else {
                binding.appName.text = "Logged in"
            }
        }
    }
}