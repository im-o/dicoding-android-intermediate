package com.rivaldy.id.dicoding.ui.home.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/** Created by github.com/im-o on 10/4/2022. */

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val api: RestApiRepositoryImpl
) : ViewModel() {

    private val _addStory: MutableLiveData<DataResource<DefaultResponse>> = MutableLiveData()
    val addStory: LiveData<DataResource<DefaultResponse>> = _addStory

    fun addStoryApiCall(descriptionBody: RequestBody, imageMultipartBody: MultipartBody.Part, lat: RequestBody?, lon: RequestBody?) = viewModelScope.launch {
        _addStory.value = DataResource.Loading
        _addStory.value = api.addStoryApiCall(descriptionBody, imageMultipartBody, lat, lon)
    }
}