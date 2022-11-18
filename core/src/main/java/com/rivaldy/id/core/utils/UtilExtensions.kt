package com.rivaldy.id.core.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rivaldy.id.core.R
import com.rivaldy.id.core.utils.UtilConstants.DATE_FORMAT_VIEW
import com.rivaldy.id.core.utils.UtilFunctions.getCircularProgressDrawable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

    fun String.formatDateToViewFromISO(): String {
        val instant = Instant.parse(this)
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_VIEW).withZone(ZoneId.of(ZoneId.systemDefault().id))
        return formatter.format(instant)
    }

    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .error(R.color.colorPrimary)
            .placeholder(getCircularProgressDrawable(this.context))
            .into(this)
    }
}