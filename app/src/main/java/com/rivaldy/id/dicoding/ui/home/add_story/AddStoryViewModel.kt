package com.rivaldy.id.dicoding.ui.home.add_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldy.id.core.data.data_source.remote.rest_api.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilFunctions.isProbablyRunningOnEmulator
import com.rivaldy.id.core.utils.UtilFunctions.reduceFileImageAndRotate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

/** Created by github.com/im-o on 10/4/2022. */

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val api: RestApiRepositoryImpl
) : ViewModel() {

    private val _addStory: MutableLiveData<DataResource<DefaultResponse>> = MutableLiveData()
    val addStory: LiveData<DataResource<DefaultResponse>> = _addStory

    fun addStoryApiCall(description: String, photoFile: File, isBackCamera: Boolean, isRotateImage: Boolean) = viewModelScope.launch {
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
        var imageRequestBody = reduceFileImageAndRotate(photoFile, isBackCamera, false).asRequestBody("image/jpeg".toMediaTypeOrNull())
        if (isProbablyRunningOnEmulator) imageRequestBody = reduceFileImageAndRotate(photoFile, isBackCamera, isRotateImage).asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", photoFile.name, imageRequestBody)

        _addStory.value = DataResource.Loading
        _addStory.value = api.addStoryApiCall(descriptionRequestBody, imageMultipart, null, null)
    }
}