package com.rivaldy.id.core.data.paging.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.rivaldy.id.core.data.datasource.local.db.AppDatabase
import com.rivaldy.id.core.data.datasource.remote.rest.ApiService
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.data.paging.StoryRemoteMediator

/** Created by github.com/im-o on 10/29/2022. */

class StoryPagingRepository(
    private val db: AppDatabase,
    private val api: ApiService
) {
    fun getStoriesPaging(): LiveData<PagingData<StoryEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(db, api),
            pagingSourceFactory = {
                db.storyDao().getStoriesPaging()
            }
        ).liveData
    }
}