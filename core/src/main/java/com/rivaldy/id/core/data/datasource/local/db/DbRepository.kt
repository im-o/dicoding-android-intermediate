package com.rivaldy.id.core.data.datasource.local.db

import com.rivaldy.id.core.data.model.local.db.StoryEntity

/** Created by github.com/im-o on 10/20/2022. */

interface DbRepository {
    suspend fun insertStoriesDb(movies: MutableList<StoryEntity>)
    suspend fun clearStoriesDb()
    suspend fun getStoriesNoLiveData(): MutableList<StoryEntity>
}