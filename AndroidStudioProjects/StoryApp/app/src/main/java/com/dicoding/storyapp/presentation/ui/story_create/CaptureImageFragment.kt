package com.dicoding.storyapp.presentation.ui.story_create

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Rational
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.FragmentCaptureImageBinding
import com.dicoding.storyapp.utils.*
import java.io.File

class CaptureImageFragment : Fragment() {

    private var _binding: FragmentCaptureImageBinding? = null
    private val binding get() = _binding!!
    private var orientationListener: OrientationEventListener? = null

    // State
    private var imageCapture: ImageCapture? = null
    private var cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var isBusy = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCaptureImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup view
        setupActions()

        // Start Camera
        activity?.window?.let { hideSystemUI(it) }
        startCamera()
    }

    private fun setupActions() {
        binding.openGallery.setOnClickListener { openGallery() }
        binding.captureImage.setOnClickListener { takePhoto() }
        binding.switchCamera.setOnClickListener {
            cameraSelector = getOppositeCamera(cameraSelector)
            startCamera()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            // Set camera configuration
            val cameraProvider = cameraProviderFuture.get()
            val rotation = binding.viewFinder.display.rotation
            val preview = Preview.Builder().setTargetRotation(rotation).build()
                .apply { setSurfaceProvider(binding.viewFinder.surfaceProvider) }
            val viewPort = ViewPort.Builder(
                Rational(Configuration.DIM_IMAGE, Configuration.DIM_IMAGE), rotation
            ).build()
            val capture = ImageCapture.Builder().run {
                setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                setTargetRotation(rotation)
            }.build().also { imageCapture = it }

            // Define listener
            setupOrientationListener()

            // Define use case group
            val useCaseGroup = UseCaseGroup.Builder().run {
                addUseCase(preview)
                addUseCase(capture)
                setViewPort(viewPort)
            }.build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, useCaseGroup)
            } catch (e: Exception) {
                Toast.makeText(
                    requireActivity(), getString(R.string.camera_error_message), Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    private fun setupOrientationListener() {
        orientationListener =
            orientationListener ?: object : OrientationEventListener(requireActivity()) {
                override fun onOrientationChanged(orientation: Int) {
                    when (orientation) {
                        in 45..134 -> {
                            imageCapture?.targetRotation = Surface.ROTATION_270
                            rotateButton(-90)
                        }
                        in 135..224 -> {
                            imageCapture?.targetRotation = Surface.ROTATION_180
                            rotateButton(180)
                        }
                        in 225..314 -> {
                            imageCapture?.targetRotation = Surface.ROTATION_90
                            rotateButton(90)
                        }
                        else -> {
                            imageCapture?.targetRotation = Surface.ROTATION_0
                            rotateButton(0)
                        }
                    }
                }
            }.apply { if (canDetectOrientation()) enable() }
    }

    private fun takePhoto() {
        if (isBusy) return else isBusy = true
        val imageCapture = imageCapture ?: return
        val photoFile = createTempFile(requireActivity())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    goToPostPage(photoFile)
                    isBusy = false
                }

                override fun onError(exception: ImageCaptureException) {
                    isBusy = false
                    Toast.makeText(
                        requireActivity(), getString(R.string.failed_to_capture), Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    /**
     * Rotate button based on orientation
     */
    private fun rotateButton(rotation: Int) {
        with(binding) {
            openGallery.animate().rotation(rotation.toFloat()).setDuration(300).start()
            captureImage.animate().rotation(rotation.toFloat()).setDuration(300).start()
            switchCamera.animate().rotation(rotation.toFloat()).setDuration(300).start()
        }
    }

    /**
     * Launch gallery
     */
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val photoFile = uriToFile(selectedImg, requireActivity())
            goToPostPage(photoFile)
        }
    }

    private fun openGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private fun goToPostPage(photoFile: File) {
        prepareImage(photoFile)
        view?.findNavController()?.navigate(
            CaptureImageFragmentDirections.actionCameraFragmentToAddDescriptionFragment(photoFile)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        orientationListener?.disable()
        orientationListener = null
        _binding = null
    }
}