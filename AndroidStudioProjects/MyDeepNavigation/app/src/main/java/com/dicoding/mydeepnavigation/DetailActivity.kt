package com.dicoding.mydeepnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mydeepnavigation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_MESSAGE = "extra_message"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitle.text = intent.getStringExtra(EXTRA_TITLE)
        binding.tvMessage.text = intent.getStringExtra(EXTRA_MESSAGE)
    }
}