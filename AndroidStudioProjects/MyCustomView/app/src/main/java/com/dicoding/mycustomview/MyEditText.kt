package com.dicoding.mycustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class MyEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var clearButtonImage: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Masukkan nama Anda"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        clearButtonImage =
            ContextCompat.getDrawable(context, R.drawable.baseline_close_24) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s?.isNotEmpty() == true) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }

        })
    }

    private fun showClearButton() {
        setButtonDrawables(endOfTheText = clearButtonImage)
    }

    private fun hideClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null,
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (compoundDrawables[2] == null) return false

        val clearButtonStart: Float
        val clearButtonEnd: Float
        var isClearButtonClicked = false

        if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
            if (event.x < clearButtonEnd) isClearButtonClicked = true
        } else {
            clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
            if (event.x > clearButtonStart) isClearButtonClicked = true
        }

        if (isClearButtonClicked) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    clearButtonImage =
                        ContextCompat.getDrawable(context, R.drawable.baseline_close_24) as Drawable
                    showClearButton()
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    clearButtonImage =
                        ContextCompat.getDrawable(context, R.drawable.baseline_close_24) as Drawable
                    if (text != null) text?.clear()
                    hideClearButton()
                    return true
                }
            }
        }

        return false
    }
}
