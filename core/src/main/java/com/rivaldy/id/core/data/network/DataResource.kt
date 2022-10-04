package com.rivaldy.id.core.data.network

import okhttp3.ResponseBody

/** Created by github.com/im-o on 10/1/2022. */

sealed class DataResource<out T> {
    data class Success<out T>(val value: T) : DataResource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val otherMessage: String?
    ) : DataResource<Nothing>()

    object Loading : DataResource<Nothing>()
}
