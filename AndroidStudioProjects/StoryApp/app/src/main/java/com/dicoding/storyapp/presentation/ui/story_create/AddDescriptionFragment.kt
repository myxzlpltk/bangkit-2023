package com.dicoding.storyapp.presentation.ui.story_create

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.databinding.FragmentAddDescriptionBinding
import com.dicoding.storyapp.utils.hideKeyboard
import com.dicoding.storyapp.utils.showKeyboard
import java.io.File

class AddDescriptionFragment : Fragment() {

    private var _binding: FragmentAddDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StoryCreateViewModel by activityViewModels()

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
        binding.topAppBar.setNavigationOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.post_action -> {
                    hideKeyboard()
                    postButton.isEnabled = false

                    val description = binding.edAddDescription.text.toString().trim()
                    viewModel.create(file, description).observe(viewLifecycleOwner) { result ->
                        if (result is ApiResponse.Success) {
                            with(requireActivity()) {
                                setResult(RESULT_OK)
                                finish()
                            }
                        } else if (result is ApiResponse.Error) {
                            postButton.isEnabled = true
                            Toast.makeText(
                                requireActivity(), result.errorMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListeners() {
        binding.edAddDescription.doAfterTextChanged { updateButton() }

        viewModel.isBusy.observe(viewLifecycleOwner) { isBusy ->
            binding.progressIndicator.visibility = if (isBusy == true) View.VISIBLE else View.GONE
        }
    }

    private fun updateButton() {
        val isNotBusy = viewModel.isBusy.value == false
        val isDescriptionValid = binding.edAddDescription.text?.isNotBlank() == true
        postButton.isEnabled = isNotBusy && isDescriptionValid
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}