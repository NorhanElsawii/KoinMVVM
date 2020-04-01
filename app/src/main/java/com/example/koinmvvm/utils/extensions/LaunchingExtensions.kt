package com.example.koinmvvm.utils.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun <reified T : Any> AppCompatActivity.launchActivityForResult(
    requestCode: Int,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> Context.launchActivities(
    stackIntents: ArrayList<Intent>,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivities(ArrayList<Intent>().also {
        it.addAll(stackIntents)
        it.add(intent)
    }.toTypedArray())
}

inline fun <reified T : Any> Fragment.launchActivityForResult(
    requestCode: Int,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = context?.let { newIntent<T>(it) }
    intent?.init()
    startActivityForResult(intent, requestCode, options)
}


inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)