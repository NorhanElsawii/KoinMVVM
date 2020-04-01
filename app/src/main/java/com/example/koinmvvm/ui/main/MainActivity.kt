package com.example.koinmvvm.ui.main

import android.app.Activity
import com.example.koinmvvm.R
import com.example.koinmvvm.utils.base.BaseActivity
import com.example.koinmvvm.utils.extensions.launchActivity
import com.example.koinmvvm.utils.extensions.replaceFragment

class MainActivity : BaseActivity() {

    companion object {
        fun start(activity: Activity?, finish: Boolean = true) {
            if (finish)
                activity?.finishAffinity()

            activity?.launchActivity<MainActivity>()
        }
    }


    override fun layoutId(): Int = R.layout.activity_main

    override fun onViewCreated() {
        replaceFragment(
            MainFragment(),
            R.id.fl_container
        )
    }
}
