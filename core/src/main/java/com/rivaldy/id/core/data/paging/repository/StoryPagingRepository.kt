package com.rivaldy.id.core.data.paging.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rivaldy.id.core.data.datasource.remote.rest.ApiService
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.data.paging.StoryPagingSource

/** Created by github.com/im-o on 10/29/2022. */

class StoryPagingRepository(private val apiService: ApiService) {
    fun getStoriesPaging(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
}