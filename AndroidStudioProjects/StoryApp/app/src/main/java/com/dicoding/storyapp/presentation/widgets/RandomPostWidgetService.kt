package com.dicoding.storyapp.presentation.widgets

import android.content.Intent
import android.widget.RemoteViewsService
import com.dicoding.storyapp.data.repository.StoryRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RandomPostWidgetService : RemoteViewsService() {

    @Inject lateinit var storyRepository: StoryRepository

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return RandomPostWidgetDataProvider(this, intent, storyRepository)
    }
}