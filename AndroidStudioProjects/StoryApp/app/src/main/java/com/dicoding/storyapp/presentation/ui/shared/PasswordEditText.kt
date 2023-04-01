package com.dicoding.storyapp.presentation.ui.shared

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.CustomPasswordEditTextBinding

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val binding = CustomPasswordEditTextBinding.inflate(LayoutInflater.from(context), this)
    val value get() = binding.passwordField.text.toString()
    var isValid = false
        private set

    init {
        binding.passwordField.doOnTextChanged { text, _, _, _ -> validate(text.toString()) }
        binding.passwordField.setOnFocusChangeListener { _, focused ->
            if (!focused) validate(binding.passwordField.text.toString())
        }
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        binding.passwordField.addTextChangedListener(watcher)
    }

    private fun validate(value: CharSequence) {
        if (value.isBlank()) {
            isValid = false
            binding.passwordContainer.error = context.getString(R.string.error_password_empty)
            binding.passwordContainer.isErrorEnabled = true
        } else if (value.length < 8) {
            isValid = false
            binding.passwordContainer.error = context.getString(R.string.error_password_invalid)
            binding.passwordContainer.isErrorEnabled = true
        } else {
            isValid = true
            binding.passwordContainer.error = null
            binding.passwordContainer.isErrorEnabled = false
        }
    }
}