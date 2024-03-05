package com.mirash.familiar.preferences

import android.content.Context
import android.content.SharedPreferences
import com.mirash.familiar.FamiliarApp
import com.mirash.familiar.tool.KEY_INVALID_INPUT_COUNT
import com.mirash.familiar.tool.KEY_USER_ID

/**
 * @author Mirash
 */
object AppPreferences : BasePreferences(FamiliarApp.instance) {

    override fun initPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun getInvalidInputsCount(): Int = getInt(KEY_INVALID_INPUT_COUNT, 0)

    fun setInvalidInputsCount(value: Int) {
        putInt(KEY_INVALID_INPUT_COUNT, value)
    }

    fun removeInvalidInputsCount() {
        remove(KEY_INVALID_INPUT_COUNT)
    }

    fun getUserId(): Long = getLong(KEY_USER_ID, 0)

    fun setUserId(value: Long) {
        putLong(KEY_USER_ID, value)
    }
}
