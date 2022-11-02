package com.rivaldy.id.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rivaldy.id.core.data.datasource.local.db.AppDatabase
import com.rivaldy.id.core.data.datasource.local.db.remotekey.RemoteKeys
import com.rivaldy.id.core.data.datasource.remote.rest.ApiService
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.utils.wrapEspressoIdlingResource

/** Created by github.com/im-o on 10/29/2022. */

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val db: AppDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, StoryEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryEntity>
    ): MediatorResult {
        wrapEspressoIdlingResource {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }
            try {
                val responseData = apiService.getStories(page, state.config.pageSize)
                val endOfPaginationReached = responseData.listStory?.isEmpty() == true
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.remoteKeysDao().deleteRemoteKeys()
                        db.storyDao().clearStories()
                    }
                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = responseData.listStory?.map {
                        RemoteKeys(id = it.id ?: return@withTransaction, prevKey = prevKey, nextKey = nextKey)
                    }
                    db.remoteKeysDao().insertAll(keys ?: return@withTransaction)
                    db.storyDao().insertStories(
                        responseData.listStory.map {
                            StoryEntity(it.id ?: return@withTransaction, it.name, it.createdAt, it.description, it.photoUrl)
                        }.toMutableList()
                    )
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (exception: Exception) {
                return MediatorResult.Error(exception)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            db.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            db.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}