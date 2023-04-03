package com.dicoding.storyapp.presentation.ui.story_create

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.FragmentAddDescriptionBinding
import com.dicoding.storyapp.utils.hideKeyboard
import com.dicoding.storyapp.utils.showKeyboard
import java.io.File

class AddDescriptionFragment : Fragment() {

    private var _binding: FragmentAddDescriptionBinding? = null
    private val binding get() = _binding!!

    // State
    private lateinit var file: File
    private lateinit var postButton: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        file = AddDescriptionFragmentArgs.fromBundle(arguments as Bundle).file
        postButton = binding.topAppBar.menu.findItem(R.id.post_action)

        setupView()
        setupActions()
        setupListeners()
    }

    private fun setupView() {
        postButton.isEnabled = false
        binding.ivAddPhoto.setImageURI(file.toUri())
        binding.edAddDescription.showKeyboard()
    }

    private fun setupActions() {
        binding.topAppBar.setNavigationOnClickListener {view ->
            view.findNavController().popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.post_action -> {
                    hideKeyboard()
                    // val description = binding.edAddDescription.text.toString().trim()
                    with(requireActivity()) {
                        setResult(RESULT_OK)
                        finish()
                    }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}