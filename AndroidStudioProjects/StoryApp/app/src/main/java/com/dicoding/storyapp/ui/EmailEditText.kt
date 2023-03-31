package com.dicoding.storyapp.ui

import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.storyapp.R

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

    companion object {
        internal val TAG = EmailEditText::class.java.simpleName
    }

    // State
    private var hasBeenFocused = false
    var isValid = false
        private set

    init {
        background = ContextCompat.getDrawable(context, R.drawable.bg_input)
        hint = context.getString(R.string.hint_email)
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    private fun validate(value: CharSequence) {
        if (!hasBeenFocused) return

        if (TextUtils.isEmpty(value)) {
            isValid = false
            error = context.getString(R.string.error_email_empty)
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            isValid = false
            error = context.getString(R.string.error_email_invalid)
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