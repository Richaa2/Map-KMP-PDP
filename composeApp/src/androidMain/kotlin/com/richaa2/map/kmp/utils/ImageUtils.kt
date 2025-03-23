package com.richaa2.map.kmp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun String.base64ToByteArray(): ByteArray {
    return Base64.decode(this, Base64.DEFAULT)
}

fun String.base64ToBitmap(): Bitmap {
    val byteArray = this.base64ToByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun ByteArray.byteArrayToBase64(): String {
    return Base64.encodeToString(this, Base64.DEFAULT)
}
fun ByteArray.byteArrayToBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Uri.uriToByteArray(context: Context): ByteArray? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(this)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.toByteArray()
    } catch (e: Exception) {
        null
    }
}