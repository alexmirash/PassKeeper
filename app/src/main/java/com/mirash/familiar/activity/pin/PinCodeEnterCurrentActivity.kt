package com.mirash.familiar.activity.pin

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
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
        binding.pinButtonBottomStart.setOnClickListener { _: View? -> onBackPressedDispatcher.onBackPressed() }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startNewFinishCurrentActivity(PinCodeEnterActivity::class.java)
            }
        })
    }

    override fun getMessageRes(): Int = R.string.pin_code_enter_current

    override fun checkPinCode(pinCode: String?) {
        if (pinCode == null || pinCode.length != PIN_CODE_SIZE) return
        val actualPinCode = EncryptedAppPreferences.pinCode
        if (actualPinCode == pinCode) {
            startNewFinishCurrentActivity(PinCodeCreateActivity::class.java)
        }
    }
}