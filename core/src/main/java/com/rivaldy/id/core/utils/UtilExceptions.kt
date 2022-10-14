package com.rivaldy.id.core.utils

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rivaldy.id.core.R
import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilConstants.OTHER_ERROR
import com.rivaldy.id.core.utils.UtilFunctions.logE
import java.io.IOException

/** Created by github.com/im-o on 10/1/2022. */

object UtilExceptions {
    class NoInternetException(message: String) : IOException(message)

    fun Activity.handleApiError(failure: DataResource.Failure): String {
        var errorMessage = ""
        logE("NoInternetException : $failure")
        if (failure.isNetworkError) {
            errorMessage = if (failure.errorCode == OTHER_ERROR) failure.otherMessage.toString() else getString(R.string.no_internet_connection)
        } else {
            try {
                val gson = Gson()
                val type = object : TypeToken<DefaultResponse>() {}.type
                val defaultResponse: DefaultResponse? = gson.fromJson(failure.errorBody?.charStream(), type)
                errorMessage = if (failure.errorCode == 401) defaultResponse?.message ?: getString(R.string.fetch_failed) else defaultResponse?.message ?: getString(R.string.something_error)
                logE("ErrorResponse NoInternetException: $defaultResponse")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return errorMessage
    }
}