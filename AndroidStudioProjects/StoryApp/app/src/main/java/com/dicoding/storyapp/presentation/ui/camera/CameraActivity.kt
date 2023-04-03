package com.dicoding.storyapp.presentation.ui.camera

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Rational
import android.view.OrientationEventListener
import android.view.Surface
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.ActivityCameraBinding
import com.dicoding.storyapp.presentation.ui.post.PostActivity
import com.dicoding.storyapp.utils.*
import com.dicoding.storyapp.utils.Configuration.DIM_IMAGE
import java.io.File

class CameraActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater) }
    private var imageCapture: ImageCapture? = null
    private var cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var isBusy = false

    private val allPermissionsGranted
        get() = REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!allPermissionsGranted) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        setupActions()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI(window)
        startCamera()
    }

    private fun setupActions() {
        binding.openGallery.setOnClickListener { openGallery() }
        binding.captureImage.setOnClickListener { takePhoto() }
        binding.switchCamera.setOnClickListener {
            cameraSelector = getFlippedCamera(cameraSelector)
            startCamera()
        }
    }

    private fun goToPostPage(photoFile: File) {
        prepareImage(photoFile)
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra(PostActivity.EXTRA_FILE, photoFile)
        }
        startActivity(intent)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val rotation = binding.viewFinder.display.rotation
            val preview = Preview.Builder().setTargetRotation(rotation).build()
                .apply { setSurfaceProvider(binding.viewFinder.surfaceProvider) }
            val viewPort = ViewPort.Builder(Rational(DIM_IMAGE, DIM_IMAGE), rotation).build()
            val capture = ImageCapture.Builder().run {
                setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                setTargetRotation(rotation)
            }.build().also { imageCapture = it }

            val listener = object : OrientationEventListener(this) {
                override fun onOrientationChanged(orientation: Int) {
                    when (orientation) {
                        in 45..134 -> {
                            capture.targetRotation = Surface.ROTATION_270
                            rotateButton(-90)
                        }
                        in 135..224 -> {
                            capture.targetRotation = Surface.ROTATION_180
                            rotateButton(180)
                        }
                        in 225..314 -> {
                            capture.targetRotation = Surface.ROTATION_90
                            rotateButton(90)
                        }
                        else -> {
                            capture.targetRotation = Surface.ROTATION_0
                            rotateButton(0)
                        }
                    }
                }
            }
            if (listener.canDetectOrientation()) listener.enable()

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
                    this, getString(R.string.camera_error_message), Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun rotateButton(rotation: Int) {
        with(binding) {
            openGallery.animate().rotation(rotation.toFloat()).setDuration(300).start()
            captureImage.animate().rotation(rotation.toFloat()).setDuration(300).start()
            switchCamera.animate().rotation(rotation.toFloat()).setDuration(300).start()
        }
    }

    private fun takePhoto() {
        if (isBusy) return else isBusy = true
        val imageCapture = imageCapture ?: return
        val photoFile = createTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    goToPostPage(photoFile)
                    isBusy = false
                }

                override fun onError(exception: ImageCaptureException) {
                    isBusy = false
                    Toast.makeText(
                        this@CameraActivity,
                        getString(R.string.failed_to_capture),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted) {
                Toast.makeText(
                    this, getString(R.string.camera_permission_error_message), Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val photoFile = uriToFile(selectedImg, this)
            goToPostPage(photoFile)
        }
    }

    private fun openGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }
}