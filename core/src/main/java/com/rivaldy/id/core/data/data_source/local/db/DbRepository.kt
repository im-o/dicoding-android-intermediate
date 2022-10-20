package com.rivaldy.id.core.data.data_source.local.db

import androidx.lifecycle.LiveData
import com.rivaldy.id.core.data.model.local.db.StoryEntity

/** Created by github.com/im-o on 10/20/2022. */

interface DbRepository {
    suspend fun insertStoriesDb(movies: MutableList<StoryEntity>)
    fun getStoriesDb(): LiveData<MutableList<StoryEntity>>
    suspend fun clearStoriesDb()
}