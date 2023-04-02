package com.dicoding.storyapp.presentation.ui.story_detail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.databinding.ActivityStoryDetailBinding
import com.dicoding.storyapp.utils.parcelable
import com.dicoding.storyapp.utils.toLocaleFormat


class StoryDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STORY = "extra_story"
    }

    private val binding by lazy { ActivityStoryDetailBinding.inflate(layoutInflater) }
    private lateinit var story: Story

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        story = intent.parcelable(EXTRA_STORY)!!
        setupView()
        setupActions()
    }

    private fun setupView() {
        Glide.with(this).load(story.photoUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.image)
        binding.name.text = story.name
        binding.description.text = story.description
        binding.dateTime.text = story.createdAt.toLocaleFormat()
        binding.image.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    private fun setupActions() {
        binding.topAppBar.setNavigationOnClickListener { finish() }
    }
}