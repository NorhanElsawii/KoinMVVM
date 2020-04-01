package com.example.koinmvvm.data.local.sharedpreference

import android.content.Context
import com.example.koinmvvm.utils.extensions.getSharedPref
import com.example.koinmvvm.utils.locale.LocaleLanguage


class SharedPreferencesUtils private constructor(private val prefHelper: PrefHelper) {

    fun setIsLoggedInUser(isLoggedInUser: Boolean) {
        prefHelper.putBoolean(IS_LOGGED_IN_USER, isLoggedInUser)
    }

    fun isLoggedInUser(): Boolean {
        return prefHelper.getBoolean(IS_LOGGED_IN_USER, false)
    }

    fun getLanguage(): String {
        return prefHelper.getString(LANGUAGE, null) ?: LocaleLanguage.getDefaultLanguage()
    }

    fun setLanguage(language: String) {
        prefHelper.putString(LANGUAGE, language)
    }

    fun clearUser() {
        setIsLoggedInUser(false)
    }


    companion object {
        const val IS_LOGGED_IN_USER = "is-logged-in-user"
        const val LANGUAGE = "language"

        private var sharedPreferencesUtils: SharedPreferencesUtils? = null

        fun getInstance(context: Context): SharedPreferencesUtils {
            if (sharedPreferencesUtils == null)
                sharedPreferencesUtils = SharedPreferencesUtils(PrefHelper(context.getSharedPref()))

            return sharedPreferencesUtils!!
        }
    }
}