package com.dicoding.storyapp.presentation.ui.shared

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.CustomNameEditTextBinding

class NameEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val binding = CustomNameEditTextBinding.inflate(LayoutInflater.from(context), this)
    val value get() = binding.nameField.text.toString()
    var isValid = false
        private set

    init {
        binding.nameField.doOnTextChanged { text, _, _, _ -> validate(text.toString()) }
        binding.nameField.setOnFocusChangeListener { _, focused ->
            if (!focused) validate(binding.nameField.text.toString())
        }
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        binding.nameField.addTextChangedListener(watcher)
    }

    private fun validate(value: CharSequence) {
        if (value.isBlank()) {
            isValid = false
            binding.nameContainer.error = context.getString(R.string.error_name_empty)
            binding.nameContainer.isErrorEnabled = true
        } else {
            isValid = true
            binding.nameContainer.error = null
            binding.nameContainer.isErrorEnabled = false
        }
    }
}