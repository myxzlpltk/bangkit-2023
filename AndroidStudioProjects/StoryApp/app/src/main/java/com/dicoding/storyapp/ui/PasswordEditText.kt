package com.dicoding.storyapp.ui

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.CustomPasswordEditTextBinding

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private var action: (() -> Unit)? = null
    private val binding = CustomPasswordEditTextBinding.inflate(LayoutInflater.from(context), this)
    private var hasBeenFocused = false
    var isValid = false
        private set

    init {
        binding.field.doOnTextChanged { text, _, _, _ -> validate(text.toString()) }
        binding.field.setOnFocusChangeListener { _, focused ->
            if (focused) hasBeenFocused = true
            else validate(binding.field.text.toString())
        }
    }

    fun ifChanged(action: () -> Unit) {
        this.action = action
    }

    private fun validate(value: CharSequence) {
        if (!hasBeenFocused) return

        if (TextUtils.isEmpty(value)) {
            isValid = false
            binding.container.error = context.getString(R.string.error_password_empty)
            binding.container.isErrorEnabled = true
        } else if (value.length < 8) {
            isValid = false
            binding.container.error = context.getString(R.string.error_password_invalid)
            binding.container.isErrorEnabled = true
        } else {
            isValid = true
            binding.container.error = null
            binding.container.isErrorEnabled = false
        }

        action?.let { it() }
    }
}