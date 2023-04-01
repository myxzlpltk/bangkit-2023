package com.dicoding.storyapp.presentation.ui.shared

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.CustomEmailEditTextBinding

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val binding = CustomEmailEditTextBinding.inflate(LayoutInflater.from(context), this)
    val value get() = binding.emailField.text.toString()
    var isValid = false
        private set

    init {
        binding.emailField.doOnTextChanged { text, _, _, _ -> validate(text.toString()) }
        binding.emailField.setOnFocusChangeListener { _, focused ->
            if (!focused) validate(binding.emailField.text.toString())
        }
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        binding.emailField.addTextChangedListener(watcher)
    }

    private fun validate(value: CharSequence) {
        if (value.isBlank()) {
            isValid = false
            binding.emailContainer.error = context.getString(R.string.error_email_empty)
            binding.emailContainer.isErrorEnabled = true
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            isValid = false
            binding.emailContainer.error = context.getString(R.string.error_email_invalid)
            binding.emailContainer.isErrorEnabled = true
        } else {
            isValid = true
            binding.emailContainer.error = null
            binding.emailContainer.isErrorEnabled = false
        }
    }
}