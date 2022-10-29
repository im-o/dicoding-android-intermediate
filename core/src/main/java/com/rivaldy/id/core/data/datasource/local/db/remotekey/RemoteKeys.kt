package com.rivaldy.id.core.data.datasource.local.db.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Created by github.com/im-o on 10/26/2022. */

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)