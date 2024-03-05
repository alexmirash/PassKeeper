package com.mirash.familiar.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.mirash.familiar.activity.pin.PinCodeCreateActivity
import com.mirash.familiar.activity.pin.PinCodeEnterActivity
import com.mirash.familiar.preferences.EncryptedAppPreferences
import com.mirash.familiar.tool.PIN_CODE_SIZE

/**
 * @author Mirash
 */
class LauncherActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pinCode = EncryptedAppPreferences.pinCode
        val activityClass = if (pinCode == null || pinCode.length != PIN_CODE_SIZE) {
            PinCodeCreateActivity::class.java
        } else {
            PinCodeEnterActivity::class.java
        }
        startActivity(Intent(this, activityClass))
        finish()
    }
}