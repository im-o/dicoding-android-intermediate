package com.rivaldy.id.core.data.datasource.remote

import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilConstants.NO_INTERNET
import com.rivaldy.id.core.utils.UtilConstants.OTHER_ERROR
import com.rivaldy.id.core.utils.UtilFunctions.logE
import com.rivaldy.id.core.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/** Created by github.com/im-o on 10/3/2022. */

abstract class SafeApiCallRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): DataResource<T> {
        return withContext(Dispatchers.IO) {
            wrapEspressoIdlingResource {
                try {
                    DataResource.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    logE(throwable.message.toString())
                    when (throwable) {
                        is HttpException -> {
                            DataResource.Failure(false, throwable.code(), throwable.response()?.errorBody(), throwable.message)
                        }
                        else -> {
                            if (throwable.message == NO_INTERNET) {
                                DataResource.Failure(true, null, null, throwable.message)
                            } else DataResource.Failure(true, OTHER_ERROR, null, throwable.message) // CHANGE THIS TO FALSE
                        }
                    }
                }
            }
        }
    }
}