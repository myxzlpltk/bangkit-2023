package com.dicoding.storyapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        val listener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val isEmailValid = binding.edLoginEmail.isValid
                val isPasswordValid = binding.edLoginPassword.isValid
                binding.signInButton.isEnabled = isEmailValid && isPasswordValid
            }
        }

        binding.edLoginEmail.addTextChangedListener(listener)
        binding.edLoginPassword.addTextChangedListener(listener)
    }
}