package com.dicoding.storyapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.features.sign_in.SignInActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startActivity(Intent(this@MainActivity, SignInActivity::class.java))
        finish()
    }
}