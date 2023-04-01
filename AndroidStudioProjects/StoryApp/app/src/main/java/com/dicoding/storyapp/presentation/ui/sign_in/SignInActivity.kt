package com.dicoding.storyapp.presentation.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.databinding.ActivitySignInBinding
import com.dicoding.storyapp.presentation.ui.main.MainActivity
import com.dicoding.storyapp.presentation.ui.sign_up.SignUpActivity
import com.dicoding.storyapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
        setupActions()
    }

    private fun setupActions() {
        binding.signInButton.setOnClickListener { login() }

        binding.signUpPromo.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
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

    private fun login() {
        // Adjust view
        hideKeyboard()
        binding.edLoginEmail.clearFocus()
        binding.edLoginPassword.clearFocus()

        // Get data
        val email = binding.edLoginEmail.value
        val password = binding.edLoginPassword.value

        // Send request
        viewModel.login(email, password).observe(this) { res ->
            when (res) {
                ApiResponse.Loading -> {
                    binding.signInButton.isEnabled = false
                }
                is ApiResponse.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ApiResponse.Error -> {
                    binding.signInButton.isEnabled = true
                    Toast.makeText(this, res.errorMessage, Toast.LENGTH_SHORT).show()
                }
                ApiResponse.Empty -> {}
            }
        }
    }
}