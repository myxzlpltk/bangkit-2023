package com.dicoding.storyapp.presentation.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.repository.StoryRepository
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


        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true
}