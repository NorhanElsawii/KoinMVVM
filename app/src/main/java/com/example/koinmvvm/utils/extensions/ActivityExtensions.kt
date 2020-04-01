package com.example.koinmvvm.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    childFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Activity?.hideSoftKeyboard() {
    this?.let {
        if (it.currentFocus != null) {
            val inputMethodManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.currentFocus!!.windowToken, 0)
        }
    }
}

fun Activity?.showSoftKeyboard() {
    val view = this?.currentFocus
    val methodManager =
        this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (view != null)
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}
