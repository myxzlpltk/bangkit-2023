package com.dicoding.storyapp.presentation.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.databinding.StoryCardItemBinding
import com.dicoding.storyapp.utils.FALLBACK_SHIMMER
import com.facebook.shimmer.ShimmerDrawable

class StoryAdapter(private val onItemClickCallback: OnItemClickCallback) :
    PagingDataAdapter<Story, StoryAdapter.ViewHolder>(STORY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        StoryCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: StoryAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { story -> holder.bind(story) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(story: Story)
    }

    inner class ViewHolder(private val binding: StoryCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
            with(binding) {
                Glide.with(image.context).load(story.photoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(ShimmerDrawable().apply { setShimmer(FALLBACK_SHIMMER) })
                    .into(image)
                name.text = story.name
                description.text = story.description
                storyCard.setOnClickListener { onItemClickCallback.onItemClicked(story) }
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