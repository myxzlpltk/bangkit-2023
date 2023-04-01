package com.dicoding.storyapp.presentation.ui.sign_up

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.databinding.ActivitySignUpBinding
import com.dicoding.storyapp.presentation.ui.main.MainActivity
import com.dicoding.storyapp.presentation.ui.sign_in.SignInActivity
import com.dicoding.storyapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
        setupActions()
    }

    private fun setupActions() {
        binding.signUpButton.setOnClickListener { register() }

        binding.signInPromo.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun setupListeners() {
        val listener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val isNameValid = binding.edRegisterName.isValid
                val isEmailValid = binding.edRegisterEmail.isValid
                val isPasswordValid = binding.edRegisterPassword.isValid
                binding.signUpButton.isEnabled = isNameValid && isEmailValid && isPasswordValid
            }
        }

        binding.edRegisterName.addTextChangedListener(listener)
        binding.edRegisterEmail.addTextChangedListener(listener)
        binding.edRegisterPassword.addTextChangedListener(listener)
    }

    private fun register() {
        // Adjust view
        hideKeyboard()
        binding.edRegisterName.clearFocus()
        binding.edRegisterEmail.clearFocus()
        binding.edRegisterPassword.clearFocus()

        // Get data
        val name = binding.edRegisterName.value
        val email = binding.edRegisterEmail.value
        val password = binding.edRegisterPassword.value

        // Send request
        viewModel.register(name, email, password).observe(this) { res ->
            when (res) {
                ApiResponse.Loading -> {
                    binding.signUpButton.isEnabled = false
                }
                is ApiResponse.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ApiResponse.Error -> {
                    binding.signUpButton.isEnabled = true
                    Toast.makeText(this, res.errorMessage, Toast.LENGTH_SHORT).show()
                }
                ApiResponse.Empty -> {}
            }
        }
    }
}
