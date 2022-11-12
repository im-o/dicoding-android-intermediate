package com.rivaldy.id.commons.util

import android.content.Context
import android.text.SpannedString
import android.util.Patterns
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color

/** Created by github.com/im-o on 10/5/2022. */

object FormatterUtils {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun spannedHintFooterAuth(context: Context, text1: String?, text2: String?): SpannedString {
        return buildSpannedString {
            color(ContextCompat.getColor(context, com.rivaldy.id.core.R.color.colorText)) {
                append("$text1")
            }.bold {
                color(ContextCompat.getColor(context, com.rivaldy.id.core.R.color.colorPrimary)) {
                    append(" $text2")
                }
            }
        }
    }
}