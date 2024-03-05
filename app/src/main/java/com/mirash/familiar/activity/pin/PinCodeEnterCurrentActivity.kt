package com.mirash.familiar.activity.pin

import android.os.Bundle
import android.view.View
import com.mirash.familiar.R
import com.mirash.familiar.preferences.EncryptedAppPreferences
import com.mirash.familiar.tool.PIN_CODE_SIZE

/**
 * @author Mirash
 */
class PinCodeEnterCurrentActivity : PinCodeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.pinButtonBottomStart.visibility = View.VISIBLE
        binding.pinButtonBottomEnd.visibility = View.GONE
        binding.pinButtonBottomStart.text = getString(R.string.cancel)
        binding.pinButtonBottomStart.setOnClickListener { view: View? -> onBackPressed() }
    }

    override fun getMessageRes(): Int = R.string.pin_code_enter_current

    override fun checkPinCode(pinCode: String?) {
        if (pinCode == null || pinCode.length != PIN_CODE_SIZE) return
        val actualPinCode = EncryptedAppPreferences.pinCode
        if (actualPinCode == pinCode) {
            startNewActivity(PinCodeCreateActivity::class.java)
        }
    }

    override fun onBackPressed() {
        startNewActivity(PinCodeEnterActivity::class.java)
    }
}