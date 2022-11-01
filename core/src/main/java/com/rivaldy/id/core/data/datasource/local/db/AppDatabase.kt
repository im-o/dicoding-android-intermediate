package com.rivaldy.id.core.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivaldy.id.core.data.datasource.local.db.dao.StoryDao
import com.rivaldy.id.core.data.datasource.local.db.remotekey.RemoteKeys
import com.rivaldy.id.core.data.datasource.local.db.remotekey.RemoteKeysDao
import com.rivaldy.id.core.data.model.local.db.StoryEntity

/** Created by github.com/im-o on 10/20/2022. */

@Database(
    entities = [StoryEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}