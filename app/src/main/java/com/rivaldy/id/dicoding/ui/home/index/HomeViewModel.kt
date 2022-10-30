package com.rivaldy.id.dicoding.ui.home.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rivaldy.id.core.data.paging.repository.StoryPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/** Created by github.com/im-o on 10/4/2022. */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyPagingRepository: StoryPagingRepository
) : ViewModel() {

    fun getStoriesPagingApiCall() = storyPagingRepository.getStoriesPaging().cachedIn(viewModelScope)
}