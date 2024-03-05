package com.mirash.familiar.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mirash.familiar.FamiliarApp
import com.mirash.familiar.tool.KEY_PIN_CODE
import java.io.IOException
import java.security.GeneralSecurityException

/**
 * @author Mirash
 */
object EncryptedAppPreferences : BasePreferences(FamiliarApp.instance) {
    var pinCode: String?
        get() = getString(KEY_PIN_CODE, null)
        set(value) {
            putString(KEY_PIN_CODE, value)
        }

    override fun initPreferences(context: Context): SharedPreferences = try {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            "agent_secure_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    } catch (e: GeneralSecurityException) {
        Log.e("Prefs", e.toString())
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    } catch (e: IOException) {
        Log.e("Prefs", e.toString())
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}
