package com.rivaldy.id.core.data.data_source.local.db

import androidx.lifecycle.LiveData
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import javax.inject.Inject

/** Created by github.com/im-o on 10/20/2022. */

class DbRepositoryImpl @Inject constructor(
    private val db: AppDatabase
) : DbRepository {

    override suspend fun insertStoriesDb(movies: MutableList<StoryEntity>) {
        db.storyDao().insertStories(movies)
    }

    override fun getStoriesDb(): LiveData<MutableList<StoryEntity>> = db.storyDao().getStories()

    override suspend fun clearStoriesDb() {
        db.storyDao().clearStories()
    }
}