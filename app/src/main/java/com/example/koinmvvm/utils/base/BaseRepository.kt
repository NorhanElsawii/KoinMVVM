package com.example.koinmvvm.utils.base

import com.example.koinmvvm.data.local.ConnectivityUtils
import com.example.koinmvvm.data.local.LocalDataUtils
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * Created by Norhan Elsawi on 23/01/2020.
 */
abstract class BaseRepository : KoinComponent {

    private val connectivityUtils: ConnectivityUtils by inject()

    val localDataUtils: LocalDataUtils by inject()

    fun isNetworkConnected(): Boolean {
        return connectivityUtils.isConnected()
    }

    fun getString(id: Int): String {
        return localDataUtils.getString(id)
    }

    fun isUserLogin(): Boolean {
        return localDataUtils.sharedPreferencesUtils.isLoggedInUser()
    }

    fun clearUserData() {
        localDataUtils.sharedPreferencesUtils.clearUser()
    }
}