package com.dicoding.storyapp.presentation.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.databinding.StoryCardItemBinding
import com.dicoding.storyapp.utils.load

class StoryAdapter(private val onItemClickCallback: OnItemClickCallback) :
    PagingDataAdapter<Story, StoryAdapter.ViewHolder>(STORY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        StoryCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StoryAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { story -> holder.bind(story) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(story: Story, itemBinding: StoryCardItemBinding)
    }

    inner class ViewHolder(private val binding: StoryCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
            with(binding) {
                ivItemPhoto.load(story.photoUrl)
                tvItemName.text = story.name
                tvItemDescription.text = story.description
                storyCard.setOnClickListener { onItemClickCallback.onItemClicked(story, this) }
            }
        }
    }

    companion object {
        private val STORY_COMPARATOR = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}