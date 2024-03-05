package com.mirash.familiar.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Mirash
 */
abstract class BasePreferences(context: Context) {
    private val preferences: SharedPreferences = this.initPreferences(context)

    protected abstract fun initPreferences(context: Context): SharedPreferences

    fun putBool(key: String?, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBool(key: String?, defaultValue: Boolean) = preferences.getBoolean(key, defaultValue)

    fun putInt(key: String?, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String?, defValue: Int) = preferences.getInt(key, defValue)

    fun putFloat(key: String?, value: Float) {
        preferences.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String?, defValue: Float) = preferences.getFloat(key, defValue)

    fun getLong(key: String?, defValue: Long) = preferences.getLong(key, defValue)

    fun putLong(key: String?, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun getString(key: String?, value: String?) = preferences.getString(key, value)

    fun putString(key: String?, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    operator fun contains(key: String?) = preferences.contains(key)

    fun remove(key: String?) {
        preferences.edit().remove(key).apply()
    }
}
