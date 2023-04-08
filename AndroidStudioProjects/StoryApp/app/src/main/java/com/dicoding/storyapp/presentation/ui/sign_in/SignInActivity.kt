package com.dicoding.storyapp.presentation.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivitySignInBinding
import com.dicoding.storyapp.presentation.ui.main.MainActivity
import com.dicoding.storyapp.presentation.ui.sign_up.SignUpActivity
import com.dicoding.storyapp.utils.hideKeyboard
import com.dicoding.storyapp.utils.hideProgress
import com.dicoding.storyapp.utils.showProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        setupListeners()
        setupActions()
    }

    private fun setupViewModel() {
        viewModel.message.observe(this) { event ->
            event.getContentIfNotHandled().let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isBusy.observe(this) { isBusy ->
            if (isBusy) {
                binding.signInButton.showProgress()
            } else {
                binding.signInButton.hideProgress()
            }
        }

        viewModel.getUser().observe(this) { user ->
            if (user != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setupActions() {
        binding.signInButton.setOnClickListener {
            // Adjust view
            hideKeyboard()
            binding.edLoginEmail.clearFocus()
            binding.edLoginPassword.clearFocus()

            // Get data
            val email = binding.edLoginEmail.value.trim()
            val password = binding.edLoginPassword.value.trim()

            // Send request
            viewModel.login(email, password)
        }

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
}
