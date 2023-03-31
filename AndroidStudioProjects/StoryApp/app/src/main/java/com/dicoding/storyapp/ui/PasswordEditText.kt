package com.dicoding.storyapp.ui

import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.storyapp.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

    companion object {
        internal val TAG = PasswordEditText::class.java.simpleName
    }

    // State
    private var hasBeenFocused = false
    var isValid = false
        private set

    init {
        background = ContextCompat.getDrawable(context, R.drawable.bg_input)
        hint = context.getString(R.string.hint_password)
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun validate(value: CharSequence) {
        if (!hasBeenFocused) return

        if (TextUtils.isEmpty(value)) {
            isValid = false
            error = context.getString(R.string.error_password_empty)
        } else if (value.length < 8) {
            isValid = false
            error = context.getString(R.string.error_password_invalid)
        } else {
            isValid = true
            error = null
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) hasBeenFocused = true
        else validate(text.toString())
    }

    override fun onTextChanged(text: CharSequence, p0: Int, p1: Int, p2: Int) {
        super.onTextChanged(text, p0, p1, p2)
        validate(text)
    }
}