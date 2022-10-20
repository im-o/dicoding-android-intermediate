package com.rivaldy.id.core.data.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivaldy.id.core.data.data_source.local.db.dao.StoryDao
import com.rivaldy.id.core.data.model.local.db.StoryEntity

/** Created by github.com/im-o on 10/20/2022. */

@Database(
    entities = [StoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}