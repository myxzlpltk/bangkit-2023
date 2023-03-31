package com.dicoding.storyapp.features.sign_up

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.databinding.ActivitySignUpBinding
import com.dicoding.storyapp.features.sign_in.SignInActivity

class SignUpActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupListeners()
        setupActions()
    }

    private fun setupActions() {
        binding.signInPromo.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
            finish()
        }
    }

    private fun setupListeners() {
        binding.edRegisterName.ifChanged { updateButton() }
        binding.edRegisterEmail.ifChanged { updateButton() }
        binding.edRegisterPassword.ifChanged { updateButton() }
    }

    private fun updateButton() {
        val isNameValid = binding.edRegisterName.isValid
        val isEmailValid = binding.edRegisterEmail.isValid
        val isPasswordValid = binding.edRegisterPassword.isValid
        binding.signUpButton.isEnabled = isNameValid && isEmailValid && isPasswordValid
    }
}