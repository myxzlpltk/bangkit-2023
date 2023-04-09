package com.dicoding.storyapp.presentation.ui.story_create

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity.RESULT_OK
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.dicoding.storyapp.R
import com.dicoding.storyapp.data.remote.ApiResponse
import com.dicoding.storyapp.databinding.FragmentAddDescriptionBinding
import com.dicoding.storyapp.utils.hideKeyboard
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.File
import java.util.*

class AddDescriptionFragment : Fragment() {

    private var _binding: FragmentAddDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StoryCreateViewModel by activityViewModels()

    // Location
    private var fusedLocationClient: FusedLocationProviderClient? = null

    // State
    private lateinit var file: File
    private lateinit var postButton: MenuItem
    private var location: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddDescriptionBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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
                    viewModel.create(file, description, location?.latitude, location?.longitude)
                        .observe(viewLifecycleOwner) { result ->
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

        binding.switchLocation.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                getMyLastLocation()
            } else {
                button.text = getString(R.string.add_location)
            }
        }
    }

    private fun setupListeners() {
        binding.edAddDescription.doAfterTextChanged {
            val isNotBusy = viewModel.isBusy.value == false
            val isDescriptionValid = binding.edAddDescription.text?.isNotBlank() == true
            postButton.isEnabled = isNotBusy && isDescriptionValid
        }

        viewModel.isBusy.observe(viewLifecycleOwner) { isBusy ->
            binding.progressIndicator.visibility = if (isBusy == true) View.VISIBLE else View.GONE
        }
    }

    /* Location Section */
    private val requestPermissionLauncher =
        registerForActivityResult(RequestMultiplePermissions()) { permissions ->
            when {
                permissions[ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }
                permissions[ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }
                else -> {
                    // No location access granted.
                    Toast.makeText(activity, R.string.location_not_granted, Toast.LENGTH_SHORT)
                        .show()
                    binding.switchLocation.text = getString(R.string.add_location)
                    binding.switchLocation.isChecked = false
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(ACCESS_FINE_LOCATION) || checkPermission(ACCESS_COARSE_LOCATION)) {
            binding.switchLocation.text = getString(R.string.searching_location)
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                this.location = location ?: return@addOnSuccessListener
                binding.switchLocation.text =
                    getString(R.string.coordinate_template, location.latitude, location.longitude)

                val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(location.latitude, location.longitude, 1) { list ->
                        list[0]?.getAddressLine(0)?.let { addressLine ->
                            binding.switchLocation.text = addressLine
                        }
                    }
                } else {
                    @Suppress("DEPRECATION") geocoder.getFromLocation(
                        location.latitude, location.longitude, 1
                    )?.get(0)?.getAddressLine(0)?.let { addressLine ->
                        binding.switchLocation.text = addressLine
                    }
                }
            }?.addOnFailureListener {
                Toast.makeText(activity, R.string.location_not_found, Toast.LENGTH_SHORT).show()
                binding.switchLocation.text = getString(R.string.add_location)
                binding.switchLocation.isChecked = false
            }
        } else {
            requestPermissionLauncher.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        fusedLocationClient = null
    }
}