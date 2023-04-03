package com.dicoding.storyapp.presentation.ui.post

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.ActivityPostBinding
import com.dicoding.storyapp.presentation.ui.dashboard.DashboardActivity
import com.dicoding.storyapp.utils.file
import com.dicoding.storyapp.utils.showKeyboard
import java.io.File

class PostActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILE = "extra_file"
    }

    private val binding by lazy { ActivityPostBinding.inflate(layoutInflater) }
    private lateinit var file: File
    private lateinit var postButton: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        file = intent.file(EXTRA_FILE)!!

        setupView()
        setupActions()
        setupListeners()
    }

    private fun setupView() {
        postButton = binding.topAppBar.menu.findItem(R.id.post_action)/*.apply { isEnabled = false }*/
        binding.ivAddPhoto.setImageURI(file.toUri())
        binding.edAddDescription.showKeyboard()
    }

    private fun setupActions() {
        binding.topAppBar.setNavigationOnClickListener { finishAfterTransition() }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.post_action -> {
                    val description = binding.edAddDescription.text.toString().trim()
                    val intent = Intent(this, DashboardActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        putExtra("EXIT", true)
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListeners() {
        binding.edAddDescription.doAfterTextChanged {
            it?.let { editable ->
                postButton.isEnabled = editable.isNotBlank()
            }
        }
    }
}