package com.dicoding.storyapp.presentation.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.storyapp.databinding.ActivityPostBinding
import com.dicoding.storyapp.utils.file
import java.io.File

class PostActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILE = "extra_file"
    }

    private val binding by lazy { ActivityPostBinding.inflate(layoutInflater) }
    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        file = intent.file(EXTRA_FILE)!!

        setupView()
    }

    private fun setupView() {
        binding.ivAddPhoto.setImageURI(file.toUri())
    }
}