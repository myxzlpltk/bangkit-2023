package com.dicoding.storyapp.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable

internal val FALLBACK_SHIMMER: Shimmer =
    Shimmer.AlphaHighlightBuilder().setDuration(1000).setBaseAlpha(0.7f).setHighlightAlpha(0.3f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setAutoStart(true).build()

fun ImageView.load(
    url: String,
    loadOnlyFromCache: Boolean = false,
    onLoadingFinished: (status: Boolean) -> Unit = {},
) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean,
        ): Boolean {
            onLoadingFinished(false)
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean,
        ): Boolean {
            onLoadingFinished(true)
            return false
        }
    }

    this.build(url).dontTransform().onlyRetrieveFromCache(loadOnlyFromCache).listener(listener)
        .into(this)
}

fun ImageView.load(url: String) {
    this.build(url).into(this)
}

fun ImageView.build(url: String): RequestBuilder<Drawable> {
    return Glide.with(this).load(url)
        .placeholder(ShimmerDrawable().apply { setShimmer(FALLBACK_SHIMMER) })
}

fun MaterialButton.showProgress(
    @ColorInt tintColor: Int = MaterialColors.getColor(
        this,
        com.google.android.material.R.attr.colorOnPrimary
    ),
) {
    val spec = CircularProgressIndicatorSpec(
        context, null, 0,
        com.google.android.material.R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall
    )

    spec.indicatorColors = intArrayOf(tintColor)

    val progressIndicatorDrawable =
        IndeterminateDrawable.createCircularDrawable(context, spec)

    icon = progressIndicatorDrawable
    iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
    textScaleX = 0.0f
    isClickable = false
}

fun MaterialButton.hideProgress() {
    icon = null
    textScaleX = 1.0f
    isClickable = true
}