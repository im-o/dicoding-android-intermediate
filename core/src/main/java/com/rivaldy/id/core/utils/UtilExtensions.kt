package com.rivaldy.id.core.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.rivaldy.id.core.R
import com.rivaldy.id.core.utils.UtilConstants.DATE_FORMAT_VIEW
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import java.util.*

/** Created by github.com/im-o on 10/1/2022. */

object UtilExtensions {
    fun Context.myToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun CoordinatorLayout.showSnackBar(message: String, action: (() -> Unit)? = null) {
        if (message.isNotEmpty()) {
            val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
            action?.let {
                snackBar.setAction(context.getString(R.string.retry)) {
                    it()
                }
            }
            snackBar.show()
        }
    }

    fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
        val intent = Intent(this, it)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
    }

    fun String.toViewFromServerDate(): String {
        val dateString = this
        val dateTime = ISODateTimeFormat.dateTime().withZoneUTC().parseDateTime(dateString).toDateTime(DateTimeZone.getDefault())
        val format = DateTimeFormat.forPattern(DATE_FORMAT_VIEW)
        return dateTime.toString(format)
    }

    private val localeID = Locale("in", "ID")
}