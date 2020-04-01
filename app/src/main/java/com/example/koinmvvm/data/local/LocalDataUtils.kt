package com.example.koinmvvm.data.local

import android.content.Context
import com.example.koinmvvm.data.local.sharedpreference.SharedPreferencesUtils

class LocalDataUtils constructor(private val context: Context) {

    val sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context)

    fun getString(id: Int): String {
        return context.getString(id)
    }
}