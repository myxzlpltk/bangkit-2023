package com.dicoding.storyapp.utils

import android.content.res.Resources
import com.facebook.shimmer.Shimmer

internal val FALLBACK_SHIMMER: Shimmer =
    Shimmer.AlphaHighlightBuilder().setDuration(1000).setBaseAlpha(0.7f).setHighlightAlpha(0.3f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setAutoStart(true).build()

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}