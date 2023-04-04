package com.dicoding.storyapp.presentation.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.dicoding.storyapp.R

/**
 * Implementation of App Widget functionality.
 */
class RandomPostWidget : AppWidgetProvider() {

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
    ) {
        // Construct the RemoteViews object
        val intent = Intent(context, RandomPostWidgetService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

        val views = RemoteViews(context.packageName, R.layout.random_post_widget)
        views.setRemoteAdapter(R.id.post_list, intent)

        // Instruct to update
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}