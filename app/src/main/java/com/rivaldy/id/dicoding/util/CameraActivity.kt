package com.rivaldy.id.dicoding.util

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.utils.UtilExtensions.myToast
import com.rivaldy.id.core.utils.UtilFunctions.timestamp
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.ActivityCameraBinding
import java.io.File

class CameraActivity : BaseActivity<ActivityCameraBinding>() {
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun getViewBinding() = ActivityCameraBinding.inflate(layoutInflater)

    override fun initData() {
        if (allPermissionsGranted()) startCamera() else ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        initClick()
    }

    private fun initClick() {
        binding.takePictureIV.setOnClickListener { takePhoto() }
        binding.switchCameraIV.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                myToast(getString(R.string.permissions_not_granted))
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all { ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }
            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                myToast(getString(R.string.failed_showing_camera))
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                myToast(getString(R.string.failed_take_picture))
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val intent = Intent()
                intent.putExtra(EXTRA_PICTURE, photoFile)
                intent.putExtra(EXTRA_IS_BACK_CAMERA, cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                setResult(CAMERA_X_RESULT, intent)
                finish()
            }
        }
        )
    }

    private fun createFile(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        val outputDirectory = if (mediaDir != null && mediaDir.exists()) mediaDir else application.filesDir
        return File(outputDirectory, "$timestamp.jpg")
    }

    companion object {
        const val EXTRA_PICTURE = "extra_picture"
        const val EXTRA_IS_BACK_CAMERA = "extra_is_back_camera"
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}