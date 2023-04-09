package com.dicoding.storyapp.presentation.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.TaskStackBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.transition.Transition
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.repository.StoryRepository
import com.dicoding.storyapp.presentation.ui.story_create.StoryCreateActivity
import com.dicoding.storyapp.presentation.ui.story_detail.StoryDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 */
@AndroidEntryPoint
class RandomPostWidget : AppWidgetProvider() {

    @Inject
    lateinit var storyRepository: StoryRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        for (appWidgetId in appWidgetIds) {
            val story = runBlocking { storyRepository.getRandom().firstOrNull() }
            if (story == null) noDataAppWidget(context, appWidgetManager, appWidgetId)
            else updateAppWidget(context, appWidgetManager, appWidgetId, story)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action == WIDGET_CLICK) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0)
            val story = runBlocking { storyRepository.getRandom().first() }
            updateAppWidget(context, appWidgetManager, appWidgetId, story)
        }
    }

    companion object {
        const val WIDGET_CLICK = "android.appwidget.action.APPWIDGET_UPDATE"
        const val WIDGET_ID_EXTRA = "widget_id_extra"
    }
}

internal fun noDataAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
) {
    val views = RemoteViews(context.packageName, R.layout.random_post_widget_empty)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    story: Story,
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.random_post_widget)
    views.setTextViewText(R.id.tv_name, story.name)
    views.setTextViewText(R.id.tv_description, story.description)

    val imgView = object : AppWidgetTarget(context, R.id.iv_photo, views, appWidgetId) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
        }
    }
    val options = RequestOptions().override(500).centerCrop().error(R.drawable.story_placeholder)
    Glide.with(context.applicationContext).asBitmap().load(story.photoUrl).apply(options)
        .into(imgView)

    // Set intent
    val openActivityFlags =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE
        else PendingIntent.FLAG_ONE_SHOT

    val pendingIntentCreate = TaskStackBuilder.create(context)
        .addNextIntentWithParentStack(Intent(context, StoryCreateActivity::class.java))
        .getPendingIntent(appWidgetId, openActivityFlags)
    views.setOnClickPendingIntent(R.id.create_post_action, pendingIntentCreate)

    val pendingIntentDetail = TaskStackBuilder.create(context)
        .addNextIntentWithParentStack(Intent(context, StoryDetailActivity::class.java).apply {
            putExtra(StoryDetailActivity.EXTRA_STORY, story)
        }).getPendingIntent(story.id.hashCode(), openActivityFlags)
    views.setOnClickPendingIntent(R.id.post_card, pendingIntentDetail)

    // Set intent refresh
    val updateWidgetFlags =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        else 0
    val intent = Intent(context, RandomPostWidget::class.java).apply {
        action = RandomPostWidget.WIDGET_CLICK
        putExtra(RandomPostWidget.WIDGET_ID_EXTRA, appWidgetId)
    }
    val pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, updateWidgetFlags)
    views.setOnClickPendingIntent(R.id.action_refresh_widget, pendingIntent)

    // Instruct to update
    appWidgetManager.updateAppWidget(appWidgetId, views)
}