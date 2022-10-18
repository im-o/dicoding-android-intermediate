package com.rivaldy.id.dicoding.ui.home.add_story

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.rivaldy.id.commons.base.BaseActivity
import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilExceptions.handleApiError
import com.rivaldy.id.core.utils.UtilExtensions.myToast
import com.rivaldy.id.core.utils.UtilExtensions.showSnackBar
import com.rivaldy.id.core.utils.UtilFunctions.rotateBitmap
import com.rivaldy.id.dicoding.databinding.ActivityAddStoryBinding
import com.rivaldy.id.dicoding.util.CameraActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AddStoryActivity : BaseActivity<ActivityAddStoryBinding>() {
    private val viewModel by viewModels<AddStoryViewModel>()
    private var photoFile: File? = null
    private var isBackCamera: Boolean = true

    override fun getViewBinding() = ActivityAddStoryBinding.inflate(layoutInflater)

    override fun initData() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()
        initObservers()
        initClick()
        initListeners()
    }

    private fun initView() {

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
                val description = binding.descriptionET.text.toString()
                viewModel.addStoryApiCall(description, photoFile ?: return@setOnClickListener, isBackCamera)
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
            photoFile = it.data?.getSerializableExtra(CameraActivity.EXTRA_PICTURE) as File
            isBackCamera = it.data?.getBooleanExtra(CameraActivity.EXTRA_IS_BACK_CAMERA, true) as Boolean
            val result = rotateBitmap(BitmapFactory.decodeFile(photoFile?.path), isBackCamera)
            binding.imagePreviewIV.setImageBitmap(result)
            binding.imagePreviewCV.isVisible = true
            validationForm()
        }
    }

    companion object {
        const val EXTRA_IS_SUCCESS_ADD_STORY = "extra_is_success_add_story"
    }
}