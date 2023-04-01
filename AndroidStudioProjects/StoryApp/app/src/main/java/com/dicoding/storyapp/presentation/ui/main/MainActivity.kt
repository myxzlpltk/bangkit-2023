package com.dicoding.storyapp.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.presentation.ui.sign_up.SignUpActivity
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
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            } else {
                binding.appName.text = "Logged in"
            }
        }
    }
}