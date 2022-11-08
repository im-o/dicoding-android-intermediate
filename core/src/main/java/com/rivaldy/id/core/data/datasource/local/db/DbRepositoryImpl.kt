package com.rivaldy.id.core.data.datasource.local.db

import com.rivaldy.id.core.data.datasource.local.db.dao.StoryDao
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import javax.inject.Inject

/** Created by github.com/im-o on 10/20/2022. */

class DbRepositoryImpl @Inject constructor(
    private val dao: StoryDao
) : DbRepository {

    override suspend fun insertStoriesDb(movies: MutableList<StoryEntity>) {
        dao.insertStories(movies)
    }

    override suspend fun clearStoriesDb() {
        dao.clearStories()
    }

    override suspend fun getStoriesNoLiveData() = dao.getStoriesNoLiveData()
}