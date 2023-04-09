package com.dicoding.storyapp.presentation.ui.story_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.databinding.ActivityStoryDetailBinding
import com.dicoding.storyapp.utils.load
import com.dicoding.storyapp.utils.parcelable
import com.dicoding.storyapp.utils.toLocaleFormat


class StoryDetailActivity : AppCompatActivity() {

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

        if (story.latitude != null && story.longitude != null) {
            binding.fab.setOnClickListener {
                val uriString = "geo:0,0?q=${story.name}@${story.latitude},${story.longitude},z=15"
                val gmmIntentUri = Uri.parse(uriString)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        } else {
            binding.fab.visibility = View.GONE
        }
    }

    private fun setupActions() {
        binding.topAppBar.setNavigationOnClickListener { finishAfterTransition() }
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}