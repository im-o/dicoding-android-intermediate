package com.rivaldy.id.core.data.model.local.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

/** Created by github.com/im-o on 10/20/2022. */

@Parcelize
@Entity(tableName = "tbl_story", primaryKeys = ["id"])
data class StoryEntity(
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "createdAt")
    val createdAt: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String?
) : Parcelable