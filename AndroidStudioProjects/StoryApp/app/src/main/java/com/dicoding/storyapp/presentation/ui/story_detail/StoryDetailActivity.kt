package com.dicoding.storyapp.presentation.ui.story_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.databinding.ActivityStoryDetailBinding
import com.dicoding.storyapp.utils.load
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

        supportPostponeEnterTransition()

        story = intent.parcelable(EXTRA_STORY)!!
        setupView()
        setupActions()
    }

    private fun setupView() {
        binding.ivDetailPhoto.load(story.photoUrl, true) { success ->
            supportStartPostponedEnterTransition()
            if (!success) {
                binding.ivDetailPhoto.post {
                    binding.ivDetailPhoto.load(story.photoUrl, false) {
                        binding.ivDetailPhoto.adjustViewBounds = true
                    }
                }
            }
        }
        binding.tvDetailName.text = story.name
        binding.tvDetailDescription.text = story.description
        binding.tvDetailDate.text = story.createdAt.toLocaleFormat()
    }

    private fun setupActions() {
        binding.topAppBar.setNavigationOnClickListener { finishAfterTransition() }
    }
}