package com.example.koinmvvm.utils.extensions

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Base64
import com.example.koinmvvm.utils.Constants
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */

//2017-10-19 15:24 56
fun String.getTimeInMilliSec(): Long {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm ss", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("Asia/Riyadh")
    return try {
        val mDate = sdf.parse(this)
        mDate!!.time
    } catch (e: ParseException) {
        -1
    }
}

fun String.getDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return if (this.getTimeInMilliSec() != -1L)
        sdf.format(Date(this.getTimeInMilliSec()))
    else
        ""
}

fun String.getTime(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return if (this.getTimeInMilliSec() != -1L)
        sdf.format(Date(this.getTimeInMilliSec()))
    else
        ""
}

fun String.getYouTubeImageUrl(): String {
    return "https://img.youtube.com/vi/$this/hqdefault.jpg"
}

fun String.share(startShare: (intent: Intent) -> Unit) {
    try {
        val i = Intent(ACTION_SEND).setType("text/plain")
        i.putExtra(Intent.EXTRA_TEXT, this)
        startShare(i)
    } catch (e: Exception) {
        //pass
    }
}

fun String.isValidEmail(): Boolean {
    return length > 0 && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return length >= 8 && Pattern.compile(Constants.PASSWORD_REGEX).matcher(this).matches()
}

fun String.isValidName(): Boolean {
    return length in 1..20
}

fun String.isValidUserName(): Boolean {
    return length in 4..20
}

fun String.isValidContactUsMsgTitle(): Boolean {
    return length in 3..50
}

fun String.isValidContactUsMsg(): Boolean {
    return length in 10..2000
}

fun String.imagePathTopBase64(): String {
    getRotateMatrix(this)
    val bitmapFromFilePath = BitmapFactory.decodeFile(this)
    val bitmap = getResizedBitmap(bitmapFromFilePath)
    val rotatedBitmap =
        Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            getRotateMatrix(this),
            true
        )

    val byteArrayOutputStream = ByteArrayOutputStream()
    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    bitmap.recycle()
    bitmapFromFilePath.recycle()
    rotatedBitmap.recycle()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)

}

private fun getResizedBitmap(image: Bitmap): Bitmap {
    var width = image.width.toFloat()
    var height = image.height.toFloat()
    val bitmapRatio = width / height
    val maxHeight = 400f
    val maxWidth = 400f
    val minHeight = 200f
    val minWidth = 200f

    if (bitmapRatio > 0) {
        width = maxWidth
        height = (width / bitmapRatio).toInt().toFloat()

        if (height < minHeight) {
            height = minHeight
            width = (height * bitmapRatio).toInt().toFloat()
        }

    } else {
        height = maxHeight
        width = (height * bitmapRatio).toInt().toFloat()

        if (width < minWidth) {
            width = minWidth
            height = (width / bitmapRatio).toInt().toFloat()
        }

    }
    return Bitmap.createScaledBitmap(image, width.toInt(), height.toInt(), true)
}

private fun getRotateMatrix(img: String): Matrix {
    val exif = ExifInterface(img)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
    val matrix = Matrix()
    when (orientation) {
        6 -> matrix.postRotate(90f)
        3 -> matrix.postRotate(180f)
        8 -> matrix.postRotate(270f)
    }
    return matrix
}
