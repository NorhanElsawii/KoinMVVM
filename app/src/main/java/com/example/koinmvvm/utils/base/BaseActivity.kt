package com.example.koinmvvm.utils.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.koinmvvm.utils.locale.LocaleHelper

/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract fun layoutId(): Int

    abstract fun onViewCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleHelper.onAttach(this)
        setContentView(layoutId())
        initLayoutDirectionAccordingToLocale()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        onViewCreated()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    private fun initLayoutDirectionAccordingToLocale() {
        val rootView = window.decorView.rootView
        rootView.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}