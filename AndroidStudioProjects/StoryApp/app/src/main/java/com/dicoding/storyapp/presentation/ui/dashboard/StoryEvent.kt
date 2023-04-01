package com.dicoding.storyapp.presentation.ui.dashboard

import androidx.paging.CombinedLoadStates
import com.dicoding.storyapp.data.entity.Story

sealed class StoryEvent {
    object SwipeToRefreshEvent : StoryEvent()
    data class LoadState(val state: CombinedLoadStates) : StoryEvent()
    data class ListItemClicked(val item: Story) : StoryEvent()
    object ScreenLoad : StoryEvent()
}