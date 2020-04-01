package com.example.koinmvvm.utils.extensions

import android.graphics.Color
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.reflect.Type
import java.util.*

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */

fun Any?.toJsonString(): String = Gson().toJson(this)

fun <T> String?.toObjectFromJson(type: Type): T = Gson().fromJson(this, type)

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L?, body: (T) -> Unit) =
    liveData?.observe(this, Observer(body))

fun Int.getFormattedNumberAccordingToLocal(): String {
    return String.format(Locale.getDefault(), "%d", this)
}

fun Float.getFormattedNumberAccordingToLocal(): String {
    return String.format(Locale.getDefault(), "%.1f", this)
}

fun Int.isColorDark(): Boolean {
    val darkness =
        1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness >= 0.5
}

fun File.getImageFilePart(): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        "image_part",
        this.name,
        this.asRequestBody("image/*".toMediaType())
    )
}

fun String.getStringPart(): RequestBody {
    return this.toRequestBody("text/plain".toMediaType())
}