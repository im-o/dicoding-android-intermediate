package com.rivaldy.id.dicoding.ui.home.addstory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilExceptions.handleApiError
import com.rivaldy.id.core.utils.UtilExtensions.myToast
import com.rivaldy.id.core.utils.UtilExtensions.showSnackBar
import com.rivaldy.id.core.utils.UtilFunctions
import com.rivaldy.id.core.utils.UtilFunctions.rotateBitmap
import com.rivaldy.id.dicoding.R
import com.rivaldy.id.dicoding.databinding.ActivityAddStoryBinding
import com.rivaldy.id.dicoding.util.CameraActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class AddStoryActivity : BaseActivity<ActivityAddStoryBinding>() {
    private val viewModel by viewModels<AddStoryViewModel>()
    private var photoFile: File? = null
    private var isBackCamera: Boolean = true
    private var isRotateImage: Boolean = false
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun getViewBinding() = ActivityAddStoryBinding.inflate(layoutInflater)

    override fun initData() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (allPermissionsGranted()) getMyCurrentMLocation() else ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        initObservers()
        initClick()
        initListeners()
    }

    private fun initObservers() {
        viewModel.addStory.observe(this) {
            when (it) {
                is DataResource.Loading -> showLoading(true, isCancelable = false)
                is DataResource.Success -> showAddStoryResponse(it.value)
                is DataResource.Failure -> showFailure(it)
            }
        }
    }

    private fun showFailure(failure: DataResource.Failure) {
        showLoading(false)
        binding.root.showSnackBar(handleApiError(failure))
    }

    private fun showAddStoryResponse(response: DefaultResponse) {
        showLoading(false)
        myToast(response.message.toString())
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_IS_SUCCESS_ADD_STORY, true)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun initClick() {
        binding.apply {
            placeholderAddImage.setOnClickListener {
                val intent = Intent(this@AddStoryActivity, CameraActivity::class.java)
                launcherIntentCameraX.launch(intent)
            }
            imagePreviewCV.setOnClickListener {
                val intent = Intent(this@AddStoryActivity, CameraActivity::class.java)
                launcherIntentCameraX.launch(intent)
            }
            uploadStoryMB.setOnClickListener {
                val descriptionRequestBody = binding.descriptionET.text.toString().toRequestBody("text/plain".toMediaType())
                val imageRequestBody = UtilFunctions.rotateAndReduceFileImage(photoFile ?: return@setOnClickListener, isRotateImage, isBackCamera).asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", photoFile?.name, imageRequestBody)
                val latRequestBody = latitude?.toString()?.toRequestBody("text/plain".toMediaType())
                val lngRequestBody = longitude?.toString()?.toRequestBody("text/plain".toMediaType())
                viewModel.addStoryApiCall(descriptionRequestBody, imageMultipart, latRequestBody, lngRequestBody)
            }
        }
    }

    private fun initListeners() {
        binding.descriptionET.doAfterTextChanged { validationForm() }
    }

    private fun validationForm() {
        binding.apply {
            uploadStoryMB.isEnabled = descriptionET.text.toString().trim().isNotEmpty() && photoFile != null
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == CameraActivity.CAMERA_X_RESULT) {
            isRotateImage = true
            photoFile = it.data?.getSerializableExtra(CameraActivity.EXTRA_PICTURE) as File
            isBackCamera = it.data?.getBooleanExtra(CameraActivity.EXTRA_IS_BACK_CAMERA, true) as Boolean
            val result = rotateBitmap(BitmapFactory.decodeFile(photoFile?.path), isBackCamera)
            binding.imagePreviewIV.setImageBitmap(result)
            binding.imagePreviewCV.isVisible = true
            validationForm()
        } else if (it.resultCode == CameraActivity.GALLERY_CODE_RESULT) {
            isRotateImage = false
            photoFile = it.data?.getSerializableExtra(CameraActivity.EXTRA_GALLERY) as File
            val result = BitmapFactory.decodeFile(photoFile?.path)
            binding.imagePreviewIV.setImageBitmap(result)
            binding.imagePreviewCV.isVisible = true
            validationForm()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                getMyCurrentMLocation()
            } else {
                myToast(getString(R.string.permissions_not_granted))
                finish()
            }
        }
    }

    private fun getMyLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
            } else myToast(getString(R.string.location_not_found))
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyCurrentMLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false) ?: LocationManager.GPS_PROVIDER)
        if (location != null) {
            latitude = location.latitude
            longitude = location.longitude
        } else {
            getMyLastLocation()
        }
    }

    companion object {
        const val EXTRA_IS_SUCCESS_ADD_STORY = "extra_is_success_add_story"
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        const val REQUEST_CODE_PERMISSIONS = 10
    }
}