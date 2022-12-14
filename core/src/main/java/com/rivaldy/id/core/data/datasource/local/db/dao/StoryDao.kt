package com.rivaldy.id.core.data.datasource.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rivaldy.id.core.data.model.local.db.StoryEntity

/** Created by github.com/im-o on 10/20/2022. */

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(movie: MutableList<StoryEntity>)

    @Query("SELECT * FROM tbl_story")
    fun getStoriesNoLiveData(): MutableList<StoryEntity>

    @Query("SELECT * FROM tbl_story")
    fun getStoriesPaging(): PagingSource<Int, StoryEntity>

    @Query("DELETE FROM tbl_story")
    suspend fun clearStories()
}