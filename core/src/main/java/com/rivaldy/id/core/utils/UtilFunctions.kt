package com.rivaldy.id.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.rivaldy.id.core.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

/** Created by github.com/im-o on 10/1/2022. */

object UtilFunctions {
    fun logE(message: String) {
        if (BuildConfig.DEBUG) Log.e(UtilConstants.LOG_MESSAGE, message)
    }

    fun openAlertDialog(context: Context, title: String?, message: String?, listener: DialogButtonClickListener) {
        val builder = AlertDialog.Builder(context, androidx.appcompat.R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                listener.onPositiveButtonClick()
            }.setNegativeButton(android.R.string.cancel) { _, _ ->
                listener.onNegativeButtonClick()
            }
        builder.create().show()
    }

    val timestamp = Calendar.getInstance().time.time

    fun reduceFileImageAndRotate(file: File, isBackCamera: Boolean, isRotateImage: Boolean? = false): File {
        var bitmap = BitmapFactory.decodeFile(file.path)
        if (isRotateImage == true) bitmap = rotateBitmap(BitmapFactory.decodeFile(file.path), isBackCamera)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
        val matrix = Matrix()
        return if (isBackCamera) {
            matrix.postRotate(90f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else {
            matrix.postRotate(-90f)
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }

    val isProbablyRunningOnEmulator: Boolean by lazy {
        // Android SDK emulator
        return@lazy ((Build.FINGERPRINT.startsWith("google/sdk_gphone_")
                && Build.FINGERPRINT.endsWith(":user/release-keys")
                && Build.MANUFACTURER == "Google" && Build.PRODUCT.startsWith("sdk_gphone_") && Build.BRAND == "google"
                && Build.MODEL.startsWith("sdk_gphone_"))
                //
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                //bluestacks
                || "QC_Reference_Phone" == Build.BOARD && !"Xiaomi".equals(
            Build.MANUFACTURER,
            ignoreCase = true
        ) //bluestacks
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.HOST.startsWith("Build") //MSI App Player
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || Build.PRODUCT == "google_sdk")
    }

    interface DialogButtonClickListener {
        fun onPositiveButtonClick()
        fun onNegativeButtonClick()
    }
}