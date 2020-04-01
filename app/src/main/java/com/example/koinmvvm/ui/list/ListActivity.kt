package com.example.koinmvvm.ui.list

import com.example.koinmvvm.R
import com.example.koinmvvm.utils.base.BaseActivity
import com.example.koinmvvm.utils.extensions.replaceFragment

class ListActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_list

    override fun onViewCreated() {
        replaceFragment(
            ListFragment(),
            R.id.fl_container
        )
    }
}
