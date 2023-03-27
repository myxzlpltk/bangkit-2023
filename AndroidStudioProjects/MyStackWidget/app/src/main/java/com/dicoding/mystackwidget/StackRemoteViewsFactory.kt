package com.dicoding.mystackwidget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val drawables = listOf(
        R.drawable.star_trek_beverly,
        R.drawable.star_trek_data,
        R.drawable.star_trek_geordi,
        R.drawable.star_trek_mccoy,
        R.drawable.star_trek_riker,
        R.drawable.star_trek_spock,
        R.drawable.star_trek_uhura,
        R.drawable.star_trek_worf,
    )
    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {
    }

    override fun onDataSetChanged() {
        drawables.forEach { drawable ->
            mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, drawable))
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extras = bundleOf(ImagesBannerWidget.EXTRA_ITEM to position)
        val fillInIntent = Intent().apply { putExtras(extras) }

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}