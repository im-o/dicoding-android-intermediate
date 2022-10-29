package com.rivaldy.id.dicoding.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rivaldy.id.core.data.datasource.local.db.DbRepositoryImpl
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.data.paging.repository.StoryPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Created by github.com/im-o on 10/4/2022. */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: RestApiRepositoryImpl,
    private val db: DbRepositoryImpl,
    private val storyPagingRepository: StoryPagingRepository
) : ViewModel() {

    private val _userStories: MutableLiveData<DataResource<UserStoryResponse>> = MutableLiveData()
    val userStories: LiveData<DataResource<UserStoryResponse>> = _userStories

    fun getStoriesApiCall(page: Int? = null, size: Int? = null, locationType: Int? = null) = viewModelScope.launch {
        _userStories.value = DataResource.Loading
        _userStories.value = api.getStoriesApiCall(page, size, locationType)
    }

    fun getStoriesPagingApiCall() = storyPagingRepository.getStoriesPaging().cachedIn(viewModelScope)

    suspend fun insertStoriesDb(movies: MutableList<StoryEntity>) {
        db.insertStoriesDb(movies)
    }

    suspend fun clearStoriesDb() {
        db.clearStoriesDb()
    }
}