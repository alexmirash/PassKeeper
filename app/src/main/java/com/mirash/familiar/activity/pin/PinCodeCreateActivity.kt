package com.mirash.familiar.activity.pin

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mirash.familiar.R
import com.mirash.familiar.preferences.EncryptedAppPreferences
import com.mirash.familiar.tool.KEY_PIN_CODE
import com.mirash.familiar.tool.PIN_CODE_SIZE
import com.mirash.familiar.tool.isPinCodeActual

/**
 * @author Mirash
 */
class PinCodeCreateActivity : PinCodeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.pinButtonBottomStart.visibility = View.GONE
        binding.pinButtonBottomEnd.visibility = View.VISIBLE
        binding.pinButtonBottomEnd.text = getString(R.string.done)
    }

    override fun getMessageRes(): Int = R.string.pin_code_create

    override fun handleEnteredPinCodeSizeChange(size: Int) {
        super.handleEnteredPinCodeSizeChange(size)
        if (size == PIN_CODE_SIZE) {
            binding.pinButtonBottomEnd.setEnabled(true)
        } else {
            binding.pinButtonBottomEnd.setEnabled(false)
            binding.pinButtonBottomEnd.setOnClickListener(null)
        }
    }

    override fun checkPinCode(pinCode: String?) {
        binding.pinButtonBottomEnd.setOnClickListener { _: View ->
            finish()
            val intent = Intent(this, PinCodeConfirmActivity::class.java)
            intent.putExtra(KEY_PIN_CODE, pinCode)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (isPinCodeActual(EncryptedAppPreferences.pinCode)) {
            startNewActivity(PinCodeEnterActivity::class.java)
        } else {
            super.onBackPressed()   //TODO check
        }
    }
}