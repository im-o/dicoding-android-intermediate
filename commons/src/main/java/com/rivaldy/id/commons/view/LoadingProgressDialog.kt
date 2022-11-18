package com.rivaldy.id.commons.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.rivaldy.id.commons.R

/** Created by github.com/im-o on 10/1/2022. */

object LoadingProgressDialog {
    @SuppressLint("InflateParams")
    fun setProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}