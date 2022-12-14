package com.rivaldy.id.dicoding.ui.home.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Created by github.com/im-o on 10/4/2022. */

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val api: RestApiRepositoryImpl
) : ViewModel() {

    private val _userStories: MutableLiveData<DataResource<UserStoryResponse>> = MutableLiveData()
    val userStories: LiveData<DataResource<UserStoryResponse>> = _userStories

    fun getStoriesApiCall(page: Int? = null, size: Int? = null, locationType: Int? = null) = viewModelScope.launch {
        _userStories.value = DataResource.Loading
        _userStories.value = api.getStoriesApiCall(page, size, locationType)
    }
}