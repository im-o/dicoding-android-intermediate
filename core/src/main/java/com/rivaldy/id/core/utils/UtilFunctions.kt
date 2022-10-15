package com.rivaldy.id.core.utils

import android.util.Log
import com.rivaldy.id.core.BuildConfig

/** Created by github.com/im-o on 10/1/2022. */

object UtilFunctions {
    fun logE(message: String) {
        if (BuildConfig.DEBUG) Log.e(UtilConstants.LOG_MESSAGE, message)
    }
}