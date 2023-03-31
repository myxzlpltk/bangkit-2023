package com.dicoding.storyapp.features.sign_up

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
}