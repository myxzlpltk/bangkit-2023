package com.dicoding.mycamera

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.dicoding.mycamera.databinding.ActivityMainBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private var getFile: File? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun uploadImage() {
        if (getFile == null) {
            Toast.makeText(
                this@MainActivity,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // Prepare data
            val myFile = reduceFileImage(getFile!!)
            val description = "Ini adalah gambar".toRequestBody("text/plain".toMediaType())
            val requestImageFile = myFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart =
                MultipartBody.Part.createFormData("photo", myFile.name, requestImageFile)

            // Send data
            val service = ApiConfig().getApiService().uploadImage(imageMultipart, description)
            service.enqueue(object : Callback<FileUploadResponse> {
                override fun onResponse(
                    call: Call<FileUploadResponse>,
                    response: Response<FileUploadResponse>,
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && !responseBody.error) {
                            Toast.makeText(
                                this@MainActivity,
                                responseBody.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, response.message(), Toast.LENGTH_SHORT)
                            .show()

                    }
                }

                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal instance Retrofit", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@MainActivity)
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.previewImageView.setImageBitmap(result)

            getFile = myFile
        }
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val myFile = File(currentPhotoPath)
                val result = BitmapFactory.decodeFile(myFile.path)
                binding.previewImageView.setImageBitmap(result)

                getFile = myFile
            }
        }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            resolveActivity(packageManager)
        }

        createTempFile(application).also {
            val photoURI = FileProvider.getUriForFile(this@MainActivity, packageName, it)
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }


    private val launcherIntentCameraX =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == CAMERA_X_RESULT) {
                val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("picture", File::class.java)
                } else {
                    @Suppress("DEPRECATION") it.data?.getSerializableExtra("picture")
                } as File
                val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

                val result = rotateBitmap(BitmapFactory.decodeFile(myFile.path), isBackCamera)
                binding.previewImageView.setImageBitmap(result)

                getFile = myFile
            }
        }

    private fun startCameraX() {
        launcherIntentCameraX.launch(Intent(this, CameraActivity::class.java))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS && !allPermissionsGranted()) {
            Toast.makeText(
                this, "Tidak mendapatkan permission.", Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

}