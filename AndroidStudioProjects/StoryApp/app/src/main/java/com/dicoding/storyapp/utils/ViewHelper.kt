package com.dicoding.storyapp.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

internal val FALLBACK_SHIMMER: Shimmer =
    Shimmer.AlphaHighlightBuilder().setDuration(1000).setBaseAlpha(0.7f).setHighlightAlpha(0.3f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setAutoStart(true).build()

/*fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}*/

/*fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}*/

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