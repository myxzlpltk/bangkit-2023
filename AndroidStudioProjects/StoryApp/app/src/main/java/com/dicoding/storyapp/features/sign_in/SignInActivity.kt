package com.dicoding.storyapp.features.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivitySignInBinding
import com.dicoding.storyapp.features.sign_up.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
        setupActions()
    }

    private fun setupActions() {
        binding.signUpPromo.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
            finish()
        }
    }

    private fun setupListeners() {
        binding.edLoginEmail.ifChanged { updateButton() }
        binding.edLoginPassword.ifChanged { updateButton() }
    }

    private fun updateButton() {
        val isEmailValid = binding.edLoginEmail.isValid
        val isPasswordValid = binding.edLoginPassword.isValid
        binding.signInButton.isEnabled = isEmailValid && isPasswordValid
    }
}