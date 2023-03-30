package com.dicoding.mysound

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


class OutlineTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {}

    override fun draw(canvas: Canvas?) {
        for (i in 0..4) {
            super.draw(canvas)
        }
    }
}
