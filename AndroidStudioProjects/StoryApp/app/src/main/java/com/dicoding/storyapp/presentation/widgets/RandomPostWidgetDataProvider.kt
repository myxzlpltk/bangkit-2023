package com.dicoding.storyapp.presentation.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.repository.StoryRepository
import com.dicoding.storyapp.utils.FALLBACK_SHIMMER
import com.facebook.shimmer.ShimmerDrawable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class RandomPostWidgetDataProvider(
    private val context: Context,
    private val intent: Intent,
    private val storyRepository: StoryRepository,
) : RemoteViewsService.RemoteViewsFactory {

    private var story: Story? = null

    private fun initializeData() {
        story = runBlocking { storyRepository.getRandom().first() }
    }

    override fun onCreate() {
        initializeData()
    }

    override fun onDataSetChanged() {
        initializeData()
    }

    override fun onDestroy() {}

    override fun getCount(): Int = if (story == null) 0 else 1

    override fun getViewAt(position: Int): RemoteViews {
        val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0)

        val views = RemoteViews(context.packageName, R.layout.random_post_widget_item)
        story?.let { story ->
            views.setTextViewText(R.id.tv_name, story.name)
            views.setTextViewText(R.id.tv_description, story.description)
            Glide.with(context).asBitmap().load(story.photoUrl).centerCrop()
                .placeholder(ShimmerDrawable().apply { setShimmer(FALLBACK_SHIMMER) }).into(object :
                    AppWidgetTarget(context.applicationContext, R.id.iv_photo, views, appWidgetId) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        super.onResourceReady(resource, transition)
                    }
                })
        }

        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true
}