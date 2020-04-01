package com.example.koinmvvm.data.local.sharedpreference

import android.content.SharedPreferences
import com.example.koinmvvm.utils.extensions.toJsonString
import com.example.koinmvvm.utils.extensions.toObjectFromJson
import java.lang.reflect.Type
import java.util.*

class PrefHelper constructor(private val pref: SharedPreferences) {

    /**
     * Put int.
     *
     * @param key   the key
     * @param value the value
     */
    fun putInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    /**
     * Gets int.
     *
     * @param key the key
     * @param def the def
     * @return the int
     */
    fun getInt(key: String, def: Int): Int {
        return pref.getInt(key, def)
    }

    /**
     * Put long.
     *
     * @param key   the key
     * @param value the value
     */
    fun putLong(key: String, value: Long) {
        pref.edit().putLong(key, value).apply()
    }

    /**
     * Gets long.
     *
     * @param key the key
     * @param def the def
     * @return the long
     */
    fun getLong(key: String, def: Long): Long {
        return pref.getLong(key, def)
    }

    /**
     * Put float.
     *
     * @param key   the key
     * @param value the value
     */
    fun putFloat(key: String, value: Float) {
        pref.edit().putFloat(key, value).apply()
    }

    /**
     * Gets float.
     *
     * @param key the key
     * @param def the def
     * @return the float
     */
    fun getFloat(key: String, def: Float): Float {
        return pref.getFloat(key, def)
    }

    /**
     * Put boolean.
     *
     * @param key   the key
     * @param value the value
     */
    fun putBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @param def the def
     * @return the boolean
     */
    fun getBoolean(key: String, def: Boolean): Boolean {
        return pref.getBoolean(key, def)
    }

    /**
     * Put string.
     *
     * @param key   the key
     * @param value the value
     */
    fun putString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @param def the def
     * @return the string
     */
    fun getString(key: String, def: String?): String? {
        return pref.getString(key, def)
    }

    /**
     * Gets string set.
     *
     * @param key the key
     * @param def the def
     * @return the string set
     */
    fun getStringSet(key: String, def: HashSet<String>): Set<String>? {
        return pref.getStringSet(key, def)
    }

    private fun putStringSet(key: String, value: HashSet<String>) {
        pref.edit().putStringSet(key, value).apply()
    }

    // Date

    /**
     * Put date.
     *
     * @param key  the key
     * @param date the date
     */
    fun putDate(key: String, date: Date) {
        pref.edit().putLong(key, date.time).apply()
    }

    /**
     * Gets date.
     *
     * @param key the key
     * @return the date
     */
    fun getDate(key: String): Date {
        return Date(pref.getLong(key, 0))
    }

    // Gson

    /**
     * Put object.
     *
     * @param <T> the type parameter
     * @param key the key
     * @param t   the t
    </T> */
    fun <T> putObject(key: String, t: T) {
        if (t == null)
            pref.edit().putString(key, "").apply()
        else
            pref.edit().putString(key, t.toJsonString()).apply()
    }

    /**
     * Gets object.
     *
     * @param <T>       the type parameter
     * @param key       the key
     * @param classOfT the type
     * @return the object
    </T> */
    fun <T> getObject(key: String, type: Type): T? {
        val objectString = pref.getString(key, null)
        return if (objectString != null && objectString.isNotEmpty()) {
            objectString.toObjectFromJson(type)
        } else null
    }


    /**
     * Clear data.
     */
    fun clearData() {
        pref.edit().clear().apply()
    }

}