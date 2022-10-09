package com.rivaldy.id.commons.util

import android.util.Patterns

/** Created by github.com/im-o on 10/5/2022. */

object FormatterUtils {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}